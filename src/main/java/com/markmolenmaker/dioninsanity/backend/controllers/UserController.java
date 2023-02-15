package com.markmolenmaker.dioninsanity.backend.controllers;

import com.markmolenmaker.dioninsanity.backend.application.UserService;
import com.markmolenmaker.dioninsanity.backend.payload.error.UserRegistrationException;
import com.markmolenmaker.dioninsanity.backend.payload.request.RegisterUserRequest;
import com.markmolenmaker.dioninsanity.backend.payload.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        List<UserResponse> userResponseList = userService.getAllUsers();
        return ResponseEntity.ok(userResponseList);
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
        UserResponse userResponse = userService.registerUser(
            registerUserRequest.getId(),
            registerUserRequest.getLogin(),
            registerUserRequest.getDisplayName(),
            registerUserRequest.getProfileImageUrl()
        );
        return ResponseEntity.ok(userResponse);
    }

    @ExceptionHandler(UserRegistrationException.class)
    public ResponseEntity<?> handleUserRegistrationException(UserRegistrationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
