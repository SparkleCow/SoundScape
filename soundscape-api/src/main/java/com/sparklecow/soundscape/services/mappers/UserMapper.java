package com.sparklecow.soundscape.services.mappers;

import com.sparklecow.soundscape.entities.user.Role;
import com.sparklecow.soundscape.entities.user.User;
import com.sparklecow.soundscape.models.user.UserRequestDto;
import com.sparklecow.soundscape.models.user.UserResponseDto;
import com.sparklecow.soundscape.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public User toUser(UserRequestDto userRequestDto){

        Role role = roleRepository.findByRoleName("USER")
                .orElseThrow(() -> new RuntimeException("Role USER not found")); // Maneja la excepción adecuadamente

        return User.builder()
                .username(userRequestDto.username())
                .email(userRequestDto.email())
                .firstName(userRequestDto.firstName())
                .lastName(userRequestDto.lastName())
                .country(userRequestDto.country())
                .birthDate(userRequestDto.birthDate())
                .password(passwordEncoder.encode(userRequestDto.password()))
                .roles(Set.of(role))
                .build();
    }

    public UserResponseDto toUserResponseDto(User user){
        return UserResponseDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .fistName(user.getFirstName())
                .lastName(user.getLastName())
                .profileImageUrl(user.getProfileImageUrl())
                .bannerImageUrl(user.getBannerImageUrl())
                .birthDate(user.getBirthDate())
                .country(user.getCountry())
                .isVerified(user.isVerified())
                .isEnabled(user.isEnabled())
                .isAccountLocked(user.isAccountLocked())
                .isCredentialsExpired(user.isCredentialsExpired())
                .roles(user.getRoles())
                .build();
    }
}
