package com.markmolenmaker.dioninsanity.backend.payload.error;

public class TwitchAccountNotFoundException extends RuntimeException {
    public TwitchAccountNotFoundException(String message) {
        super(message);
    }
}
