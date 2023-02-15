package com.markmolenmaker.dioninsanity.backend.payload.response;

import com.markmolenmaker.dioninsanity.backend.payload.response.cluebingo.BingoCardResponse;

import java.util.ArrayList;
import java.util.List;

public class UserResponse {

    private String id;
    private String twitchId;
    private String username;
    private String displayName;
    private String profileImageUrl;
    private List<String> roles;

    private List<BingoCardResponse> bingoCards = new ArrayList<>();

    public UserResponse(String id, String twitchId, String username, String displayName, String profileImageUrl, List<String> roles) {
        this.id = id;
        this.twitchId = twitchId;
        this.username = username;
        this.displayName = displayName;
        this.profileImageUrl = profileImageUrl;
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTwitchId() {
        return twitchId;
    }

    public void setTwitchId(String twitchId) {
        this.twitchId = twitchId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<BingoCardResponse> getBingoCards() {
        return bingoCards;
    }

    public void setBingoCards(List<BingoCardResponse> bingoCards) {
        this.bingoCards = bingoCards;
    }

}
