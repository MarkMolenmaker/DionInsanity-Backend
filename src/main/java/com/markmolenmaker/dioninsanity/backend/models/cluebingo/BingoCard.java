package com.markmolenmaker.dioninsanity.backend.models.cluebingo;

import com.markmolenmaker.dioninsanity.backend.models.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "bingo_cards")
public class BingoCard {

    @Id
    private String id;

    @DBRef
    private User owner;

    @DBRef
    private List<Item> layout = new ArrayList<>();

    public BingoCard() {}

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

    public List<Item> getLayout() {
        return layout;
    }

    public void setLayout(List<Item> layout) {
        this.layout = layout;
    }

}
