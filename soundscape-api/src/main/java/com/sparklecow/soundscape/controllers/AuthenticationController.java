package com.sparklecow.soundscape.controllers;

import com.sparklecow.soundscape.models.user.AuthenticationRequestDto;
import com.sparklecow.soundscape.models.user.AuthenticationResponseDto;
import com.sparklecow.soundscape.models.user.UserRequestDto;
import com.sparklecow.soundscape.models.user.UserResponseDto;
import com.sparklecow.soundscape.services.user.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRequestDto userRequestDto) throws MessagingException {
        return ResponseEntity.ok(userService.create(userRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> loginUser(@RequestBody AuthenticationRequestDto authenticationRequestDto){
        return ResponseEntity.ok(userService.login(authenticationRequestDto));
    }

    @PostMapping("/validateToken")
    public ResponseEntity<Void> validateToken(@RequestParam String token) throws MessagingException {
        userService.validateToken(token);
        return ResponseEntity.ok().build();
    }
}
