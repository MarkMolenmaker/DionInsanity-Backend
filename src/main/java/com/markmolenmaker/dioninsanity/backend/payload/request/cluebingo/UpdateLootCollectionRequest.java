package com.markmolenmaker.dioninsanity.backend.payload.request.cluebingo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class UpdateLootCollectionRequest {

    @NotBlank
    private String owner;

    @NotBlank
    private String item;

    @NotBlank
    private String action;

    @NotBlank
    @Positive
    private int amount;

    public String getOwner() {
        return owner;
    }

    public String getItem() {
        return item;
    }

    public String getAction() {
        return action;
    }

    public int getAmount() {
        return amount;
    }
}
