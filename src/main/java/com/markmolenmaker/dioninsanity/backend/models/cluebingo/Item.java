package com.markmolenmaker.dioninsanity.backend.models.cluebingo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "items")
public class Item {

    @Id
    private String id;

    private EItem name;

    public Item() {
    }

    public Item(EItem name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EItem getName() {
        return name;
    }

    public void setName(EItem name) {
        this.name = name;
    }

}
