package com.sparklecow.soundscape.models.user;

import java.time.LocalDate;

public record UserRequestDto(
        String username,
        String email,
        String password,
        String firstName,
        String lastName,
        LocalDate birthDate,
        String country
){
}