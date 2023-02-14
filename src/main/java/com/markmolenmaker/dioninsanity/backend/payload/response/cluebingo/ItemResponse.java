package com.markmolenmaker.dioninsanity.backend.payload.response.cluebingo;

public class ItemResponse {
    private String name;

        public ItemResponse(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
}
