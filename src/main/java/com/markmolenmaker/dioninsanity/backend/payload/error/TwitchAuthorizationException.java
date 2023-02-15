package com.markmolenmaker.dioninsanity.backend.payload.error;

public class TwitchAuthorizationException extends RuntimeException {
    public TwitchAuthorizationException(String message) {
        super(message);
    }
}
