package com.markmolenmaker.dioninsanity.backend.payload.response;

public class TwitchAccountResponse {

    private String id;
    private String login;
    private String displayName;
    private String profileImageUrl;

    public TwitchAccountResponse(String id, String login, String displayName, String profileImageUrl) {
        this.id = id;
        this.login = login;
        this.displayName = displayName;
        this.profileImageUrl = profileImageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
}
