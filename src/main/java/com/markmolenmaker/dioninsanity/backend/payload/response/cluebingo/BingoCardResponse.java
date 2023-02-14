package com.markmolenmaker.dioninsanity.backend.payload.response.cluebingo;

import java.util.Map;

public class BingoCardResponse {

    private String id;
    private String owner;
    private String[] layout;
    private Map<String, Integer> loot;

    public BingoCardResponse(String id, String owner, String[] layout, Map<String, Integer> loot) {
        this.id = id;
        this.owner = owner;
        this.layout = layout;
        this.loot = loot;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String user) {
        this.owner = user;
    }

    public String[] getLayout() {
        return layout;
    }

    public void setLayout(String[] layout) {
        this.layout = layout;
    }

    public Map<String, Integer> getLoot() {
        return loot;
    }

    public void setLoot(Map<String, Integer> loot) {
        this.loot = this.loot;
    }
}
