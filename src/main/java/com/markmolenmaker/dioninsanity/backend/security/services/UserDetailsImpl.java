package com.markmolenmaker.dioninsanity.backend.security.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.markmolenmaker.dioninsanity.backend.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private String id;
	private String twitchId;
	private String username;
	@JsonIgnore
	private String password;
	private String displayName;
	private String email;
	private String profileImageUrl;
	@JsonIgnore
	private String accessToken;
	@JsonIgnore
	private String refreshToken;

	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(String id, String twitchId, String username, String password, String displayName, String email,
						   String profileImageUrl, String accessToken, String refreshToken,
						   Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.twitchId = twitchId;
		this.username = username;
		this.password = password;
		this.displayName = displayName;
		this.email = email;
		this.profileImageUrl = profileImageUrl;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.authorities = authorities;
	}

	public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());

		return new UserDetailsImpl(
				user.getId(),
				user.getTwitchId(),
				user.getUsername(),
				user.getPassword(),
				user.getDisplayName(),
				user.getEmail(),
				user.getProfileImageUrl(),
				user.getAccessToken(),
				user.getRefreshToken(),
				authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public String getId() {
		return id;
	}

	public String getTwitchId() {
		return twitchId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}
}
