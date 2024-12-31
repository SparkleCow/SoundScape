package com.sparklecow.soundscape.models.user;

public record UserUpdateDto(
        String email,
        String password,
        String firstName,
        String lastName,
        String profileImageUrl,
        String bannerImageUrl
) {}