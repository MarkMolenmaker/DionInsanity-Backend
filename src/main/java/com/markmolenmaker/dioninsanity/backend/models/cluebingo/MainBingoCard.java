package com.markmolenmaker.dioninsanity.backend.models.cluebingo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "mainbingocard")
public class MainBingoCard {

    @Id
    private String id;

    private String name = "mainbingocard";

    @DBRef
    private List<Loot> loot = new ArrayList<>();

    public MainBingoCard() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Loot> getLoot() {
        return loot;
    }

    public void setLoot(List<Loot> loot) {
        this.loot = loot;
    }
}
