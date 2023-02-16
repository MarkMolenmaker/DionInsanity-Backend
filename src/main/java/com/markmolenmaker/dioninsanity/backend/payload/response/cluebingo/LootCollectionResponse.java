package com.markmolenmaker.dioninsanity.backend.payload.response.cluebingo;

import java.util.Map;

public class LootCollectionResponse {

    private String id;
    private String owner;
    private Map<String, Integer> loot;

    public LootCollectionResponse(String id, String owner, Map<String, Integer> loot) {
        this.id = id;
        this.owner = owner;
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

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Map<String, Integer> getLoot() {
        return loot;
    }

    public void setLoot(Map<String, Integer> loot) {
        this.loot = loot;
    }
}
