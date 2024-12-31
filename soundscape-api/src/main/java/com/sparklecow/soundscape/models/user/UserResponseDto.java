package com.sparklecow.soundscape.models.user;

import lombok.Builder;

import java.time.LocalDate;

import java.util.Set;

@Builder
public record UserResponseDto(
        String username,
        String email,
        String fistName,
        String lastName,
        String profileImageUrl,
        String bannerImageUrl,
        LocalDate birthDate,
        String country,
        Boolean isVerified,
        Boolean isEnabled,
        Boolean isAccountLocked,
        Boolean isCredentialsExpired,
        Set<Role> roles
) {}
