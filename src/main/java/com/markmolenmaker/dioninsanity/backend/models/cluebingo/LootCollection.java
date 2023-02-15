package com.markmolenmaker.dioninsanity.backend.models.cluebingo;

import com.markmolenmaker.dioninsanity.backend.models.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;

@Document(collection = "loot_collections")
public class LootCollection {

    @Id
    private String id;

    @DBRef
    private User owner;

    private HashMap<String, Integer> items = new HashMap<>();

    public LootCollection() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public HashMap<String, Integer> getItems() {
        return items;
    }

    public void setItems(HashMap<String, Integer> items) {
        this.items = items;
    }

    public void increaseItemAmount(String item, int amount) {
        if (items.containsKey(item)) {
            items.put(item, items.get(item) + amount);
        } else {
            items.put(item, amount);
        }
    }

    public void decreaseItemAmount(String item, int amount) {
        if (items.containsKey(item)) {
            items.put(item, Math.max(items.get(item) - amount, 0)); // Don't go below 0
        } else {
            items.put(item, -amount);
        }
    }

    public void reset() {
        items.replaceAll((k, v) -> 0);
    }
}
