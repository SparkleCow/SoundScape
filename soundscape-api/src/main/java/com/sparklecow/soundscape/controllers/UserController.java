package com.sparklecow.soundscape.controllers;

import com.sparklecow.soundscape.entities.user.User;
import com.sparklecow.soundscape.models.user.UserResponseDto;
import com.sparklecow.soundscape.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    /*Size of pagination elements*/
    private final Integer SIZE = 20;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> findUsers(@RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
                                                           @RequestParam(required = false, name = "username") String username){
        Pageable pageable = PageRequest.of(page, SIZE, Sort.by("id").descending());
        if(username.isEmpty()) return ResponseEntity.ok(userService.findAll(pageable));
        return ResponseEntity.ok(userService.findByUsername(username, pageable));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getUserInformation(Authentication authentication){
        return ResponseEntity.ok(userService.getUserInformation(authentication));
    }

    /*It will remove the registered account. It requires the authenticated user and uses it to delete it by its ID*/
    @DeleteMapping("/delete/me")
    public ResponseEntity<Void> deleteUser(Authentication authentication){
        User user = (User) authentication;
        userService.deleteById(user.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
