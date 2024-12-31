package com.sparklecow.soundscape.services.user;

import com.sparklecow.soundscape.config.jwt.JwtUtils;
import com.sparklecow.soundscape.entities.user.Token;
import com.sparklecow.soundscape.entities.user.User;
import com.sparklecow.soundscape.models.email.EmailTemplateName;
import com.sparklecow.soundscape.models.user.*;
import com.sparklecow.soundscape.repositories.TokenRepository;
import com.sparklecow.soundscape.repositories.UserRepository;
import com.sparklecow.soundscape.services.email.EmailService;
import com.sparklecow.soundscape.services.mappers.UserMapper;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final JwtUtils jwtUtils;
    private final EmailService emailService;
    private final TokenRepository tokenRepository;
    @Value("${application.mailing.activation-url}")
    private String activationUrl;

    @Override
    public AuthenticationResponseDto login(AuthenticationRequestDto authenticationRequestDto) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                authenticationRequestDto.username(), authenticationRequestDto.password()
        );

        authenticationManager.authenticate(auth);

        UserDetails user = userRepository.findByUsername(authenticationRequestDto.username())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new AuthenticationResponseDto(jwtUtils.generateToken(user));
    }

    @Override
    public UserResponseDto create(UserRequestDto userRequestDto) throws MessagingException {
        User user = userMapper.toUser(userRequestDto);
        userRepository.save(user);
        sendValidation(user);
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
    public List<UserResponseDto> findAll() {
        return List.of();
    }

    @Override
    public UserResponseDto findById(Long id) {
        return null;
    }

    @Override
    public UserResponseDto updateById(UserUpdateDto userUpdateDto, Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
