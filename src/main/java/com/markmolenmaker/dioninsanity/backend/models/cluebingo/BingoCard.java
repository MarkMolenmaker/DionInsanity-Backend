package com.markmolenmaker.dioninsanity.backend.models.cluebingo;

import com.markmolenmaker.dioninsanity.backend.models.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "bingocards")
public class BingoCard {

    @Id
    private String id;

    @DBRef
    private User user;

    @DBRef
    private List<Loot> loot = new ArrayList<>();

    public BingoCard() {
    }

    public BingoCard(User user) {
        this.user = user;
        for (int i = 0; i < 25; i++) {
            // Add a random loot item to the card, but make sure it's not a duplicate
            Loot lootItem = new Loot(ELoot.getRandomELoot());
            while (loot.contains(lootItem)) {
                lootItem = new Loot(ELoot.getRandomELoot());
            }
            loot.add(lootItem);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Loot> getLoot() {
        return loot;
    }

    public void setLoot(List<Loot> loot) {
        this.loot = loot;
    }
}
