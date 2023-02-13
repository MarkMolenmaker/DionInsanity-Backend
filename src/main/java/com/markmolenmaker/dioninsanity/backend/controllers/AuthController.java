package com.markmolenmaker.dioninsanity.backend.controllers;

import com.markmolenmaker.dioninsanity.backend.application.TwitchAuthorizationService;
import com.markmolenmaker.dioninsanity.backend.payload.request.TwitchLoginRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.markmolenmaker.dioninsanity.backend.payload.response.JwtResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	TwitchAuthorizationService twitchAuthorizationService;

	@PostMapping("/twitch")
	public ResponseEntity<?> authenticateWithTwitch(@Valid @RequestBody TwitchLoginRequest twitchLoginRequest) {
		JwtResponse jwtResponse = twitchAuthorizationService.authorizeWithTwitchCode(twitchLoginRequest.getCode());
		return ResponseEntity.ok(jwtResponse);
	}
}
