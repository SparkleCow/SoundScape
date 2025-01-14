package com.sparklecow.soundscape.controllers;

import com.sparklecow.soundscape.models.user.UserResponseDto;
import com.sparklecow.soundscape.repositories.UserRepository;
import com.sparklecow.soundscape.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponseDto> getUserInformation(Authentication authentication){
        return ResponseEntity.ok(userService.findUser(authentication));
    }
}
