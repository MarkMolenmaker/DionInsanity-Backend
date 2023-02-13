package com.markmolenmaker.dioninsanity.backend.payload.response;

import java.util.List;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String id;
	private String twitchId;
	private String username;
	private String displayName;
	private String email;
	private String profileImageUrl;
	private List<String> roles;

	public JwtResponse(String accessToken, String id, String twitchId, String username, String displayName,
					   String email, String profileImageUrl, List<String> roles) {
		this.token = accessToken;
		this.id = id;
		this.twitchId = twitchId;
		this.username = username;
		this.displayName = displayName;
		this.email = email;
		this.profileImageUrl = profileImageUrl;
		this.roles = roles;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTwitchId() {
		return twitchId;
	}

	public void setTwitchId(String twitchId) {
		this.twitchId = twitchId;
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
}
