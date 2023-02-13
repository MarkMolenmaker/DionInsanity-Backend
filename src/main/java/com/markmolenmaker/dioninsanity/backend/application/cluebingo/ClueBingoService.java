package com.markmolenmaker.dioninsanity.backend.application.cluebingo;

import com.markmolenmaker.dioninsanity.backend.models.cluebingo.BingoCard;
import com.markmolenmaker.dioninsanity.backend.models.cluebingo.ELoot;
import com.markmolenmaker.dioninsanity.backend.models.cluebingo.Loot;
import com.markmolenmaker.dioninsanity.backend.models.cluebingo.MainBingoCard;
import com.markmolenmaker.dioninsanity.backend.payload.response.cluebingo.BingoCardResponse;
import com.markmolenmaker.dioninsanity.backend.repository.cluebingo.BingoCardRepository;
import com.markmolenmaker.dioninsanity.backend.repository.cluebingo.LootRepository;
import com.markmolenmaker.dioninsanity.backend.repository.cluebingo.MainBingoCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClueBingoService {

    @Autowired
    MainBingoCardRepository mainBingoCardRepository;

    @Autowired
    LootRepository lootRepository;

    public BingoCardResponse createMainBingoCard() {
        if (!mainBingoCardRepository.existsByName("mainbingocard")) {
            MainBingoCard bingoCard = new MainBingoCard();

            List<Loot> loot = new ArrayList<>();
            for (int i = 0; i < 25; i++) {
                // Add a random loot item to the card, but make sure it's not a duplicate
                Loot lootItem = new Loot(ELoot.getRandomELoot());
                while (loot.contains(lootItem)) {
                    lootItem = new Loot(ELoot.getRandomELoot());
                }
                Loot lootItemDB = lootRepository.findByName(lootItem.getName().toString());
                loot.add(lootItemDB);
            }
            bingoCard.setLoot(loot);

            mainBingoCardRepository.save(bingoCard);

            String[] lootNames = new String[25];
            for (int i = 0; i < 25; i++) {
                lootNames[i] = bingoCard.getLoot().get(i).getName().toString();
            }
            return new BingoCardResponse(
                    bingoCard.getId(),
                    null,
                    lootNames
            );
        } else {
            throw new RuntimeException("Main bingo card already exists");
        }
    }

    public BingoCardResponse getMainBingoCard() {
        MainBingoCard bingoCard = mainBingoCardRepository.findByName("mainbingocard");
        if (bingoCard != null) {
            String[] lootNames = new String[25];
            for (int i = 0; i < 25; i++) {
                lootNames[i] = bingoCard.getLoot().get(i).getName().toString();
            }
            return new BingoCardResponse(
                    bingoCard.getId(),
                    null,
                    lootNames
            );
        } else {
            throw new RuntimeException("Main bingo card does not exist");
        }
    }
}
