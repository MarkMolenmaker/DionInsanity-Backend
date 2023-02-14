package com.markmolenmaker.dioninsanity.backend.application;

import com.markmolenmaker.dioninsanity.backend.models.User;
import com.markmolenmaker.dioninsanity.backend.payload.error.TwitchAccountNotFoundException;
import com.markmolenmaker.dioninsanity.backend.payload.response.TwitchAccountResponse;
import com.markmolenmaker.dioninsanity.backend.repository.UserRepository;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwitchInformationService {

    @Autowired
    UserRepository userRepository;

    @Value("${markmolenmaker.dioninsanity.backend.twitchClientId}")
    private String twitchClientId;

    public TwitchAccountResponse searchTwitchAccount(String username, String login) throws TwitchAccountNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Error: User is not found."));

        try {
            // Request user info from Twitch
            Unirest.setTimeouts(0, 0);
            HttpResponse<JsonNode> userInfoResponse = Unirest.get("https://api.twitch.tv/helix/users?login=" + login)
                    .header("Authorization", "Bearer " + user.getAccessToken())
                    .header("Client-ID", twitchClientId)
                    .asJson();

            // User info
            String twitchId = userInfoResponse.getBody().getObject().getJSONArray("data").getJSONObject(0).getString("id");
            String twitchLogin = userInfoResponse.getBody().getObject().getJSONArray("data").getJSONObject(0).getString("login");
            String displayName = userInfoResponse.getBody().getObject().getJSONArray("data").getJSONObject(0).getString("display_name");
            String profileImageUrl = userInfoResponse.getBody().getObject().getJSONArray("data").getJSONObject(0).getString("profile_image_url");

            return new TwitchAccountResponse(
                    twitchId,
                    twitchLogin,
                    displayName,
                    profileImageUrl
            );

        } catch (Exception e) {
            throw new TwitchAccountNotFoundException("Twitch account " + login + " not found.");
        }

    }

}
