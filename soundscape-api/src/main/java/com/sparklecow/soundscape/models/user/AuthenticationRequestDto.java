package com.sparklecow.soundscape.models.user;

public record AuthenticationRequestDto(
        String username,
        String password
) {
}
