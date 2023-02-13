package com.markmolenmaker.dioninsanity.backend.application;

import com.markmolenmaker.dioninsanity.backend.models.ERole;
import com.markmolenmaker.dioninsanity.backend.models.Role;
import com.markmolenmaker.dioninsanity.backend.models.User;
import com.markmolenmaker.dioninsanity.backend.payload.response.JwtResponse;
import com.markmolenmaker.dioninsanity.backend.repository.RoleRepository;
import com.markmolenmaker.dioninsanity.backend.repository.UserRepository;
import com.markmolenmaker.dioninsanity.backend.security.jwt.JwtUtils;
import com.markmolenmaker.dioninsanity.backend.security.services.UserDetailsImpl;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TwitchAuthorizationService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Value("${markmolenmaker.dioninsanity.backend.twitchClientId}")
    private String twitchClientId;

    @Value("${markmolenmaker.dioninsanity.backend.twitchClientSecret}")
    private String twitchClientSecret;

    @Value("${markmolenmaker.dioninsanity.backend.twitchRedirectUri}")
    private String twitchRedirectUri;

    @Value("${markmolenmaker.dioninsanity.backend.password}")
    private String password;

    public JwtResponse authorizeWithTwitchCode(String code) {
        try {
            // Request oauth token from Twitch
            Unirest.setTimeouts(0, 0);
            HttpResponse<JsonNode> response = Unirest.post("https://id.twitch.tv/oauth2/token")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .field("client_id", twitchClientId)
                    .field("client_secret", twitchClientSecret)
                    .field("code", code)
                    .field("grant_type", "authorization_code")
                    .field("redirect_uri", twitchRedirectUri)
                    .asJson();

            // Token info
            String accessToken = response.getBody().getObject().getString("access_token");
            String refreshToken = response.getBody().getObject().getString("refresh_token");

            // Request user info from Twitch
            Unirest.setTimeouts(0, 0);
            HttpResponse<JsonNode> userInfoResponse = Unirest.get("https://api.twitch.tv/helix/users")
                    .header("Authorization", "Bearer " + accessToken)
                    .header("Client-Id", twitchClientId)
                    .asJson();

            // User info
            String twitchId = userInfoResponse.getBody().getObject().getJSONArray("data").getJSONObject(0).getString("id");
            String username = userInfoResponse.getBody().getObject().getJSONArray("data").getJSONObject(0).getString("login");
            String displayName = userInfoResponse.getBody().getObject().getJSONArray("data").getJSONObject(0).getString("display_name");
            String email = userInfoResponse.getBody().getObject().getJSONArray("data").getJSONObject(0).getString("email");
            String profileImageUrl = userInfoResponse.getBody().getObject().getJSONArray("data").getJSONObject(0).getString("profile_image_url");

            // If User doesn't exist, create new user
            if (!userRepository.existsByUsername(username)) {
                // Create new user's account
                User user = new User(twitchId, username, encoder.encode(password), displayName, email, profileImageUrl, encoder.encode(accessToken), encoder.encode(refreshToken));

                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                user.setRoles(Collections.singleton(userRole));

                userRepository.save(user);
            }

            // Get user from database
            User user = userRepository.findByUsername(username).get();

            // Signin
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), password));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getTwitchId(),
                    userDetails.getUsername(),
                    userDetails.getDisplayName(),
                    userDetails.getEmail(),
                    userDetails.getProfileImageUrl(),
                    roles);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
