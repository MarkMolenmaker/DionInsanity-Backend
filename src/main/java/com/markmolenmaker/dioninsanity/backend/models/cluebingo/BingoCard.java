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

    @DBRef
    private List<Item> bonusLayout = new ArrayList<>();

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

        // Pick 5 unique items from the layout and add them to the bonus layout
        List<Item> copy = new ArrayList<>(layout);
        for (int i = 0; i < 5; i++) {
            int randomIndex = (int) (Math.random() * copy.size());
            Item item = copy.get(randomIndex);
            copy.remove(randomIndex);
            this.bonusLayout.add(item);
        }
    }

    public List<Item> getBonusLayout() {
        return bonusLayout;
    }

    public void setBonusLayout(List<Item> bonusLayout) {
        this.bonusLayout = bonusLayout;
    }

}
