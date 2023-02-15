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
import java.util.stream.Collectors;

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

        // Delete all Bingo Cards
        bingoCardRepository.deleteAll();

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

        // Reset the LootCollection for the General Bingo Card
        LootCollection generalBingoCardLootCollection = lootCollectionRepository.findByOwner(generalBingoCardOwner);
        generalBingoCardLootCollection.reset();
        lootCollectionRepository.save(generalBingoCardLootCollection);

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

    public BingoCardResponse addBingoCardToUser(String id) {
        // Find the user
        User owner = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find user with id: " + id));

        // Create a new General Bingo Card
        BingoCard bingoCard = new BingoCard();
        bingoCard.setOwner(owner);

        // Generate a random layout for the Bingo Card
        List<Item> layout = new ArrayList<>();

        // Layout based on the General Bingo Card
        List<EItem> items = Arrays.stream(getGeneralBingoCard().getLayout()).map(EItem::valueOf).collect(Collectors.toList());
        for (int i = 0; i < 25; i++) {
            int randomIndex = (int) (Math.random() * items.size());
            Item item = itemRepository.findByName(items.get(randomIndex).name());
            items.remove(randomIndex);
            layout.add(item);
        }
        bingoCard.setLayout(layout);

        bingoCardRepository.save(bingoCard);

        return getBingoCard(bingoCard.getId());
    }

    public List<BingoCardResponse> getBingoCardsByOwner(User owner) {

        // Find the  Bingo Cards
        List<BingoCard> bingoCardList = bingoCardRepository.findAllByOwner(owner);

        // Find the User's loot collection
        LootCollection bingoCardLootCollection = lootCollectionRepository.findByOwner(owner);

        // Create a list of BingoCardResponse
        List<BingoCardResponse> bingoCardResponseList = new ArrayList<>();
        for (BingoCard bingoCard : bingoCardList) {
            BingoCardResponse bingoCardResponse = new BingoCardResponse(
                    bingoCard.getId(),
                    bingoCard.getOwner().getUsername(),
                    bingoCard.getLayout().stream().map(item -> item.getName().toString()).toArray(String[]::new),
                    bingoCardLootCollection.getItems()
            );
            bingoCardResponseList.add(bingoCardResponse);
        }

        return bingoCardResponseList;
    }

    public void deleteBingoCardById(String bingoCardId) {
        bingoCardRepository.deleteById(bingoCardId);
    }

    public List<BingoCardResponse> getAllBingoCards() {
        List<BingoCard> bingoCards = bingoCardRepository.findAll();

        // Get General Bingo Card's loot collection
        User generalBingoCardOwner = userRepository.findByUsername("general_bingo_card_owner")
                .orElseThrow(() -> new RuntimeException("Could not find user with username: general_bingo_card_owner"));
        LootCollection lootCollection = lootCollectionRepository.findByOwner(generalBingoCardOwner);

        List<BingoCardResponse> bingoCardResponses = new ArrayList<>();
        for (BingoCard bingoCard : bingoCards) {
            if (bingoCard.getOwner().getUsername().equals("general_bingo_card_owner"))
                continue;
            bingoCardResponses.add(new BingoCardResponse(
                    bingoCard.getId(),
                    bingoCard.getOwner().getUsername(),
                    bingoCard.getLayout().stream().map(item -> item.getName().toString()).toArray(String[]::new),
                    lootCollection.getItems()
            ));
        }
        return bingoCardResponses;
    }
}
