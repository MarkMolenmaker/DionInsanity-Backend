package com.markmolenmaker.dioninsanity.backend.payload.error;

public class UserRegistrationException extends RuntimeException {
    public UserRegistrationException(String message) {
        super(message);
    }
}
