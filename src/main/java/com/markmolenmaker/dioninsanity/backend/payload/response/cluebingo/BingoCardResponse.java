package com.markmolenmaker.dioninsanity.backend.payload.response.cluebingo;

import java.util.Map;

public class BingoCardResponse {

    private String id;
    private String owner;
    private String[] layout;

    private String[] bonusLayout;
    private Map<String, Integer> loot;

    public BingoCardResponse(String id, String owner, String[] layout, String[] bonusLayout, Map<String, Integer> loot) {
        this.id = id;
        this.owner = owner;
        this.layout = layout;
        this.bonusLayout = bonusLayout;
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

    public String[] getBonusLayout() {
        return bonusLayout;
    }

    public void setBonusLayout(String[] bonusLayout) {
        this.bonusLayout = bonusLayout;
    }

    public Map<String, Integer> getLoot() {
        return loot;
    }

    public void setLoot(Map<String, Integer> loot) {
        this.loot = this.loot;
    }

    public int getScore() {
        int unique_score = 0;
        int duplicate_score = 0;
        int bonus_score = 0;

        // Calculate the loot score
        for (String loot_item : loot.keySet()) {
            if (loot.get(loot_item) > 0) {
                unique_score += 2;
                duplicate_score += loot.get(loot_item) - 1;
                if (bonusLayout != null && bonusLayout.length > 0) {
                    for (String bonus_item : bonusLayout) {
                        if (loot_item.equals(bonus_item)) {
                            bonus_score += 1;
                        }
                    }
                }
            }
        }

        // Calculate the row score
        int row_score = 0;
        // Horizontal
        for (int i = 0; i < 5; i++) {
            String[] row = new String[5];
            for (int j = 0; j < 5; j++) {
                row[j] = layout[i * 5 + j];
            }
            int row_score_temp = getRowScore(row);
            if (row_score_temp > row_score) {
                row_score = row_score_temp;
            }
        }
        // Vertical
        for (int i = 0; i < 5; i++) {
            String[] row = new String[5];
            for (int j = 0; j < 5; j++) {
                row[j] = layout[j * 5 + i];
            }
            int row_score_temp = getRowScore(row);
            if (row_score_temp > row_score) {
                row_score = row_score_temp;
            }
        }

        // Calculate the diagonal score
        int diagonal_score = 0;
        String[] diagonal = new String[5];
        for (int i = 0; i < 5; i++) {
            diagonal[i] = layout[i * 5 + i];
        }
        diagonal_score = getRowScore(diagonal);

        // Calculate the anti-diagonal score
        int anti_diagonal_score = 0;
        String[] anti_diagonal = new String[5];
        for (int i = 0; i < 5; i++) {
            anti_diagonal[i] = layout[i * 5 + 4 - i];
        }
        anti_diagonal_score = getRowScore(anti_diagonal);

        // Calculate the total score
        return unique_score + duplicate_score + bonus_score + row_score + diagonal_score + anti_diagonal_score;
    }

    private int getRowScore(String[] row) {
        for (String loot_item : row)
            if (loot.get(loot_item) < 1)
                return 0;
        return 5;
    }

}