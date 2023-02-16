package com.markmolenmaker.dioninsanity.backend.application.cluebingo;

import com.markmolenmaker.dioninsanity.backend.models.User;
import com.markmolenmaker.dioninsanity.backend.models.cluebingo.EItem;
import com.markmolenmaker.dioninsanity.backend.models.cluebingo.Item;
import com.markmolenmaker.dioninsanity.backend.models.cluebingo.BingoCard;
import com.markmolenmaker.dioninsanity.backend.models.cluebingo.LootCollection;
import com.markmolenmaker.dioninsanity.backend.payload.response.cluebingo.BingoCardResponse;
import com.markmolenmaker.dioninsanity.backend.payload.response.cluebingo.ItemResponse;
import com.markmolenmaker.dioninsanity.backend.payload.response.cluebingo.LootCollectionResponse;
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

    /** The Local methods **/
    private User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find user with id: " + id));
    }

    private User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Could not find user with username: " + username));
    }

    private BingoCard getBingoCardById(String id) {
        return bingoCardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find Bingo Card with id: " + id));
    }

    private BingoCard getBingoCardByOwner(User owner) {
        return bingoCardRepository.findByOwner(owner)
                .orElseThrow(() -> new RuntimeException("Could not find Bingo Card with owner: " + owner.getUsername()));
    }

    private LootCollection getLootCollectionByOwner(User owner) {
        return lootCollectionRepository.findByOwner(owner)
                .orElseThrow(() -> new RuntimeException("Could not find Loot Collection with owner: " + owner.getUsername()));
    }

    public LootCollection createLootCollection(User user) {
        LootCollection lootCollection = new LootCollection();
        lootCollection.setOwner(user);

        // Add all items to the Loot Collection
        List<Item> items = itemRepository.findAll();
        for (Item item : items) {
            lootCollection.putItem(item.getName().name(), 0);
        }
        return lootCollectionRepository.save(lootCollection);
    }

    /** The Public Controller methods **/
    public BingoCardResponse generateGeneralBingoCard(String[] items_scope) {
        // Get the General Bingo Card owner
        User generalBingoCardOwner = getUserByUsername("general_bingo_card_owner");

        // If a General Bingo Card already exists, delete all bingo cards
        if (bingoCardRepository.existsByOwner(generalBingoCardOwner))
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
        LootCollection generalBingoCardLootCollection = getLootCollectionByOwner(generalBingoCardOwner);
        generalBingoCardLootCollection.reset();
        lootCollectionRepository.save(generalBingoCardLootCollection);

        return getGeneralBingoCard();
    }

    public BingoCardResponse getBingoCard(String id) {
        // Find the  Bingo Card
        BingoCard bingoCard = getBingoCardById(id);

        // Find the General Bingo Card's loot collection
        LootCollection bingoCardLootCollection = getLootCollectionByOwner(bingoCard.getOwner());

        return new BingoCardResponse(
                bingoCard.getId(),
                bingoCard.getOwner().getUsername(),
                bingoCard.getLayout().stream().map(item -> item.getName().toString()).toArray(String[]::new),
                bingoCard.getBonusLayout().stream().map(item -> item.getName().toString()).toArray(String[]::new),
                bingoCardLootCollection.getItems()

        );
    }

    public BingoCardResponse getGeneralBingoCard() {
        // Get the general bingo card owner
        User generalBingoCardOwner = getUserByUsername("general_bingo_card_owner");

        // Get the general bingo card
        BingoCard generalBingoCard = getBingoCardByOwner(generalBingoCardOwner);

        // Get the general bingo card owner's loot collection
        LootCollection generalBingoCardLootCollection = getLootCollectionByOwner(generalBingoCardOwner);

        return new BingoCardResponse(
                generalBingoCard.getId(),
                generalBingoCardOwner.getUsername(),
                generalBingoCard.getLayout().stream().map(item -> item.getName().toString()).toArray(String[]::new),
                generalBingoCard.getBonusLayout().stream().map(item -> item.getName().toString()).toArray(String[]::new),
                generalBingoCardLootCollection.getItems()

        );
    }

    public List<BingoCardResponse> getAllGeneralBingoCardVariations() {
        // Get the general bingo card owner
        User generalBingoCardOwner = getUserByUsername("general_bingo_card_owner");

        // Get the general bingo card
        BingoCard generalBingoCard = getBingoCardByOwner(generalBingoCardOwner);

        List<BingoCardResponse> bingoCardResponses = new ArrayList<>();

        // Get all users
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getUsername().equals("general_bingo_card_owner")) continue;
            LootCollection lootCollection = getLootCollectionByOwner(user);
            bingoCardResponses.add(new BingoCardResponse(
                    generalBingoCard.getId(),
                    user.getUsername(),
                    generalBingoCard.getLayout().stream().map(item -> item.getName().toString()).toArray(String[]::new),
                    generalBingoCard.getBonusLayout().stream().map(item -> item.getName().toString()).toArray(String[]::new),
                    lootCollection.getItems()
            ));
        }

        return bingoCardResponses;
    }

    public void updateLootCollection(String username, String itemName, String action, int amount) {
        // Find the User by the username
        User user = getUserByUsername(username);

        // Find the Loot Collection by the user
        LootCollection lootCollection = getLootCollectionByOwner(user);

        // Alter the Loot Collection
        if (action.equalsIgnoreCase("increase"))
            lootCollection.increaseItemAmount(itemName, amount);
        else if (action.equalsIgnoreCase("decrease"))
            lootCollection.decreaseItemAmount(itemName, amount);
        else
            throw new RuntimeException("Invalid action: " + action);

        lootCollectionRepository.save(lootCollection);
    }

    public List<ItemResponse> getAllItems() {
        List<Item> items = itemRepository.findAll();
        List<ItemResponse> itemResponses = new ArrayList<>();
        for (Item item : items) {
            itemResponses.add(new ItemResponse(item.getName().toString()));
        }
        return itemResponses;
    }

    public BingoCardResponse addBingoCardToUser(String id) {
        // Find the user
        User owner = getUserById(id);

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
        LootCollection bingoCardLootCollection = getLootCollectionByOwner(owner);

        // Create a list of BingoCardResponse
        List<BingoCardResponse> bingoCardResponseList = new ArrayList<>();
        for (BingoCard bingoCard : bingoCardList) {
            BingoCardResponse bingoCardResponse = new BingoCardResponse(
                    bingoCard.getId(),
                    bingoCard.getOwner().getUsername(),
                    bingoCard.getLayout().stream().map(item -> item.getName().toString()).toArray(String[]::new),
                    bingoCard.getBonusLayout().stream().map(item -> item.getName().toString()).toArray(String[]::new),
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
        User generalBingoCardOwner = getUserByUsername("general_bingo_card_owner");
        LootCollection lootCollection = getLootCollectionByOwner(generalBingoCardOwner);

        List<BingoCardResponse> bingoCardResponses = new ArrayList<>();
        for (BingoCard bingoCard : bingoCards) {
            if (bingoCard.getOwner().getUsername().equals("general_bingo_card_owner"))
                continue;
            bingoCardResponses.add(new BingoCardResponse(
                    bingoCard.getId(),
                    bingoCard.getOwner().getUsername(),
                    bingoCard.getLayout().stream().map(item -> item.getName().toString()).toArray(String[]::new),
                    bingoCard.getBonusLayout().stream().map(item -> item.getName().toString()).toArray(String[]::new),
                    lootCollection.getItems()
            ));
        }
        return bingoCardResponses;
    }

    public List<LootCollectionResponse> getAllLootCollections() {
        List<LootCollection> lootCollections = lootCollectionRepository.findAll();

        List<LootCollectionResponse> lootCollectionResponses = new ArrayList<>();
        for (LootCollection lootCollection : lootCollections) {
            if (lootCollection.getOwner().getUsername().equalsIgnoreCase("general_bingo_card_owner")) continue;
            lootCollectionResponses.add(new LootCollectionResponse(
                    lootCollection.getId(),
                    lootCollection.getOwner().getUsername(),
                    lootCollection.getItems()
            ));
        }

        return lootCollectionResponses;
    }

    // Temp
    public void registerItems() {
        for (EItem eItem : EItem.values()) {
            if (itemRepository.existsByName(eItem.name())) continue;
            Item item = new Item(eItem);
            itemRepository.save(item);
        }
    }

    // Temp
    public void registerGeneralBingoCardOwner() {
        // Create the General Bingo Card owner
        User generalBingoCardOwner = new User();
        generalBingoCardOwner.setUsername("general_bingo_card_owner");
        userRepository.save(generalBingoCardOwner);

        // Create the General Loot Collection
        createLootCollection(generalBingoCardOwner);
    }

    // Temp
    public void resetAllLootCollections() {
        // Delete all Loot Collections
        lootCollectionRepository.deleteAll();

        // Create a new Loot Collection for each User
        List<User> users = userRepository.findAll();
        for (User user : users) {
            createLootCollection(user);
        }
    }

}
