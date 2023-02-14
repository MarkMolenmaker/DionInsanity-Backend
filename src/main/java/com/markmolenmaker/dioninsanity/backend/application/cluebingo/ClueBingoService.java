package com.markmolenmaker.dioninsanity.backend.application.cluebingo;

import com.markmolenmaker.dioninsanity.backend.models.User;
import com.markmolenmaker.dioninsanity.backend.models.cluebingo.EItem;
import com.markmolenmaker.dioninsanity.backend.models.cluebingo.Item;
import com.markmolenmaker.dioninsanity.backend.models.cluebingo.BingoCard;
import com.markmolenmaker.dioninsanity.backend.models.cluebingo.LootCollection;
import com.markmolenmaker.dioninsanity.backend.payload.response.cluebingo.BingoCardResponse;
import com.markmolenmaker.dioninsanity.backend.payload.response.cluebingo.ItemResponse;
import com.markmolenmaker.dioninsanity.backend.repository.UserRepository;
import com.markmolenmaker.dioninsanity.backend.repository.cluebingo.BingoCardRepository;
import com.markmolenmaker.dioninsanity.backend.repository.cluebingo.ItemRepository;
import com.markmolenmaker.dioninsanity.backend.repository.cluebingo.LootCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClueBingoService {

    @Autowired
    BingoCardRepository bingoCardRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    LootCollectionRepository lootCollectionRepository;

    @Autowired
    UserRepository userRepository;

    public BingoCardResponse generateGeneralBingoCard(String[] items_scope) {
        // Find the General Bingo Card owner
        User generalBingoCardOwner = userRepository.findByUsername("general_bingo_card_owner")
                .orElseThrow(() -> new RuntimeException("Could not find user with username: general_bingo_card_owner"));

        // Check if a General Bingo Card already exists
        if (bingoCardRepository.existsByOwner(generalBingoCardOwner)) {
            BingoCard generalBingoCard = bingoCardRepository.findByOwner(generalBingoCardOwner);
            bingoCardRepository.delete(generalBingoCard);
        }

        // Create a new General Bingo Card
        BingoCard generalBingoCard = new BingoCard();
        generalBingoCard.setOwner(generalBingoCardOwner);

        // Generate a random layout for the General Bingo Card
        List<Item> layout = new ArrayList<>();

        List<EItem> items;
        if (items_scope.length == 0) items = new ArrayList<>(List.of(EItem.values()));
        else  items = new ArrayList<>(List.of(Arrays.stream(items_scope).map(EItem::valueOf).toArray(EItem[]::new)));
        for (int i = 0; i < 25; i++) {
            int randomIndex = (int) (Math.random() * items.size());
            Item item = itemRepository.findByName(items.get(randomIndex).name());
            items.remove(randomIndex);
            layout.add(item);
        }
        generalBingoCard.setLayout(layout);

        bingoCardRepository.save(generalBingoCard);

        return getGeneralBingoCard();
    }

    public BingoCardResponse getBingoCard(String id) {
        // Find the  Bingo Card
        BingoCard bingoCard = bingoCardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find bingo card with id: " + id));

        // Find the General Bingo Card's loot collection
        LootCollection bingoCardLootCollection = lootCollectionRepository.findByOwner(bingoCard.getOwner());

        return new BingoCardResponse(
                bingoCard.getId(),
                bingoCard.getOwner().getUsername(),
                bingoCard.getLayout().stream().map(item -> item.getName().toString()).toArray(String[]::new),
                bingoCardLootCollection.getItems()

        );
    }

    public BingoCardResponse getGeneralBingoCard() {

        // Find the General Bingo Card owner
        User generalBingoCardOwner = userRepository.findByUsername("general_bingo_card_owner")
                .orElseThrow(() -> new RuntimeException("Could not find user with username: general_bingo_card_owner"));

        // Find the General Bingo Card
        BingoCard generalBingoCard = bingoCardRepository.findByOwner(generalBingoCardOwner);

        // Find the General Bingo Card's loot collection
        LootCollection generalBingoCardLootCollection = lootCollectionRepository.findByOwner(generalBingoCardOwner);

        return new BingoCardResponse(
                generalBingoCard.getId(),
                generalBingoCardOwner.getUsername(),
                generalBingoCard.getLayout().stream().map(item -> item.getName().toString()).toArray(String[]::new),
                generalBingoCardLootCollection.getItems()

        );
    }

    public BingoCardResponse updateLootCollection(String bingoCardId, String username, String itemName, String action, int amount) {
        // Find the User by the username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Could not find user with username: " + username));

        // Find the Loot Collection by the user
        LootCollection lootCollection = lootCollectionRepository.findByOwner(user);

        if (action.equalsIgnoreCase("increase"))
            lootCollection.increaseItemAmount(itemName, amount);
        else if (action.equalsIgnoreCase("decrease"))
            lootCollection.decreaseItemAmount(itemName, amount);
        else
            throw new RuntimeException("Invalid action: " + action);

        lootCollectionRepository.save(lootCollection);

        return getBingoCard(bingoCardId);
    }

    public List<ItemResponse> getAllItems() {
        List<Item> items = itemRepository.findAll();
        List<ItemResponse> itemResponses = new ArrayList<>();
        for (Item item : items) {
            itemResponses.add(new ItemResponse(item.getName().toString()));
        }
        return itemResponses;
    }

    // Temp
    public void registerItems() {
        for (EItem eItem : EItem.values()) {
            Item item = new Item(eItem);
            itemRepository.save(item);
        }
    }

    // Temp
    public void registerGeneralBingoCardOwner() {
        // Create the General Bingo Card owner
        User user = new User();
        user.setUsername("general_bingo_card_owner");
        userRepository.save(user);

        // Find the General Bingo Card owner
        User generalBingoCardOwner = userRepository.findByUsername("general_bingo_card_owner")
                .orElseThrow(() -> new RuntimeException("Could not find user with username: general_bingo_card_owner"));

        // Create the General Loot Collection
        LootCollection lootCollection = new LootCollection();
        lootCollection.setOwner(generalBingoCardOwner);
        lootCollectionRepository.save(lootCollection);
    }

}
