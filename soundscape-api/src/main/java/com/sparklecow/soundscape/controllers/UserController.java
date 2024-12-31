package com.sparklecow.soundscape.controllers;

import com.sparklecow.soundscape.models.user.AuthenticationRequestDto;
import com.sparklecow.soundscape.models.user.AuthenticationResponseDto;
import com.sparklecow.soundscape.models.user.UserRequestDto;
import com.sparklecow.soundscape.models.user.UserResponseDto;
import com.sparklecow.soundscape.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRequestDto userRequestDto){
        return ResponseEntity.ok(userService.create(userRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> loginUser(@RequestBody AuthenticationRequestDto authenticationRequestDto){
        return ResponseEntity.ok(userService.login(authenticationRequestDto));
    }
}
