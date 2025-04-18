package com.sparklecow.soundscape.services.user;

import com.sparklecow.soundscape.config.jwt.JwtUtils;
import com.sparklecow.soundscape.entities.user.Token;
import com.sparklecow.soundscape.entities.user.User;
import com.sparklecow.soundscape.exceptions.*;
import com.sparklecow.soundscape.models.email.EmailTemplateName;
import com.sparklecow.soundscape.models.user.*;
import com.sparklecow.soundscape.repositories.TokenRepository;
import com.sparklecow.soundscape.repositories.UserRepository;
import com.sparklecow.soundscape.services.email.EmailService;
import com.sparklecow.soundscape.services.mappers.UserMapper;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final JwtUtils jwtUtils;
    private final EmailService emailService;
    private final TokenRepository tokenRepository;
    @Value("${application.mailing.activation-url}")
    private String activationUrl;

    public AuthenticationResponseDto login(AuthenticationRequestDto authenticationRequestDto) throws AuthenticationException{
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authenticationRequestDto.username(), authenticationRequestDto.password());
        UserDetails userDetails  = (UserDetails)  authenticationManager.authenticate(authToken).getPrincipal();
        return new AuthenticationResponseDto(jwtUtils.generateToken(userDetails));
    }

    @Override
    public UserResponseDto createAdmin(UserRequestDto userRequestDto){
        User user = userMapper.toAdmin(userRequestDto);
        userRepository.save(user);
        try{
            sendValidation(user);
        } catch (MessagingException e) {
            throw new EmailSendingException(e.getMessage());
        }
        return userMapper.toUserResponseDto(user);
    }

    @Override
    public Page<UserResponseDto> findByUsername(String username, Pageable pageable) {
        return userRepository.findByUsernameContainingIgnoreCase(username, pageable).map(userMapper::toUserResponseDto);
    }

    @Override
    public UserResponseDto create(UserRequestDto userRequestDto){
        User user = userMapper.toUser(userRequestDto);
        userRepository.save(user);
        try{
            sendValidation(user);
        } catch (MessagingException e) {
            throw new EmailSendingException(e.getMessage());
        }
        return userMapper.toUserResponseDto(user);
    }

    @Override
    public void sendValidation(User user) throws MessagingException {
        String token = saveAndGenerateToken(user);
        emailService.sendEmail(user.getEmail(), "Activación de cuenta", user.getUsername(),
                token, activationUrl, EmailTemplateName.ACTIVATE_ACCOUNT);
    }

    @Override
    public String saveAndGenerateToken(User user) {
        String tokenInfo = generateToken(6);
        Token token = Token.builder()
                .token(tokenInfo)
                .user(user)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .build();
        tokenRepository.save(token);
        return tokenInfo;
    }

    @Override
    public String generateToken(Integer tokenLength) {
        String characters = "0123456789";
        StringBuilder token = new StringBuilder();
        SecureRandom sr = new SecureRandom();
        for (int i = 0; i < tokenLength; i++) {
            int randomIndex = sr.nextInt(characters.length());
            token.append(characters.charAt(randomIndex));
        }
        return token.toString();
    }

    @Override
    @Transactional
    public void validateToken(String tokenCode) throws MessagingException {
        Token token = tokenRepository.findByToken(tokenCode).orElseThrow(() -> new TokenNotFoundException("Token not found"));

        if(token.getValidatedAt()!=null){
            throw new InvalidTokenException("Token has been validated before");
        }
        if(token.getExpiresAt().isBefore(LocalDateTime.now())){
            sendValidation(token.getUser());
            throw new ExpiredTokenException("Token has expired");
        }
        token.setValidatedAt(LocalDateTime.now());
        User user = token.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        tokenRepository.save(token);
    }

    @Override
    public UserResponseDto getUserInformation(Authentication authentication) {
        if(authentication==null){
            throw new IllegalOperationException("Authentication request cannot be null");
        }
        return userMapper.toUserResponseDto((User) authentication.getPrincipal());
    }

    @Override
    public Page<UserResponseDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toUserResponseDto);
    }

    @Override
    public UserResponseDto findById(Long id) {
        return userMapper.toUserResponseDto( userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User with id "+id+" not found")));
    }

    @Override
    public UserResponseDto updateById(UserUpdateDto userUpdateDto, Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if(userOpt.isEmpty()){
            throw new UsernameNotFoundException("User with id "+id+" not found");
        }
        userRepository.deleteById(id);
    }
}
