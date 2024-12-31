package com.sparklecow.soundscape.services.user;

import com.sparklecow.soundscape.config.jwt.JwtUtils;
import com.sparklecow.soundscape.entities.user.User;
import com.sparklecow.soundscape.models.user.*;
import com.sparklecow.soundscape.repositories.UserRepository;
import com.sparklecow.soundscape.services.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final JwtUtils jwtUtils;

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
    public UserResponseDto create(UserRequestDto userRequestDto) {
        User user = userMapper.toUser(userRequestDto);
        userRepository.save(user);
        return userMapper.toUserResponseDto(user);
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
