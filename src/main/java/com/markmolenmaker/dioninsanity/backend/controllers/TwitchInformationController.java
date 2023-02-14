package com.markmolenmaker.dioninsanity.backend.controllers;

import com.markmolenmaker.dioninsanity.backend.application.TwitchInformationService;
import com.markmolenmaker.dioninsanity.backend.payload.error.TwitchAccountNotFoundException;
import com.markmolenmaker.dioninsanity.backend.payload.response.TwitchAccountResponse;
import com.markmolenmaker.dioninsanity.backend.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/twitch")
public class TwitchInformationController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    TwitchInformationService twitchInformationService;

    @GetMapping("/search")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> searchTwitchAccount(@RequestHeader (name="Authorization") String token, @RequestParam String login) {
        String username = jwtUtils.getUserNameFromJwtToken(token.split(" ")[1].trim());
        TwitchAccountResponse twitchAccountResponse = twitchInformationService.searchTwitchAccount(username, login);
        return ResponseEntity.ok(twitchAccountResponse);
    }

    @ExceptionHandler(TwitchAccountNotFoundException.class)
    public ResponseEntity<?> handleTwitchAccountNotFoundException(TwitchAccountNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
