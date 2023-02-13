package com.markmolenmaker.dioninsanity.backend.payload.request;

import jakarta.validation.constraints.NotBlank;

public class TwitchLoginRequest {
	@NotBlank
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
