package com.markmolenmaker.dioninsanity.backend.payload.request;

public class RegisterUserRequest {

    private String id;
    private String login;
    private String displayName;
    private String profileImageUrl;

    public String getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}
