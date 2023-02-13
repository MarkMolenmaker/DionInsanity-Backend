package com.markmolenmaker.dioninsanity.backend.models.cluebingo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "loot")
public class Loot {

    @Id
    private String id;

    private ELoot name;

    public Loot() {
    }

    public Loot(ELoot name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ELoot getName() {
        return name;
    }

    public void setName(ELoot name) {
        this.name = name;
    }

}
