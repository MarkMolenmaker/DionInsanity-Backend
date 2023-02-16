package com.markmolenmaker.dioninsanity.backend.controllers.cluebingo;

import com.markmolenmaker.dioninsanity.backend.application.cluebingo.ClueBingoService;
import com.markmolenmaker.dioninsanity.backend.payload.request.cluebingo.GenerateGeneralBingoCardRequest;
import com.markmolenmaker.dioninsanity.backend.payload.request.cluebingo.UpdateLootCollectionRequest;
import com.markmolenmaker.dioninsanity.backend.payload.response.cluebingo.BingoCardResponse;
import com.markmolenmaker.dioninsanity.backend.payload.response.cluebingo.ItemResponse;
import com.markmolenmaker.dioninsanity.backend.payload.response.cluebingo.LootCollectionResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/cluebingo")
public class ClueBingoController {

    @Autowired
    ClueBingoService clueBingoService;

    @GetMapping("/general")
    public ResponseEntity<?> getGeneralBingoCard() {
        BingoCardResponse bingoCardResponse = clueBingoService.getGeneralBingoCard();
        return ResponseEntity.ok(bingoCardResponse);
    }

    @GetMapping("/general/all")
    public ResponseEntity<?> getAllGeneralBingoCardVariations() {
        List<BingoCardResponse> bingoCardResponseList = clueBingoService.getAllGeneralBingoCardVariations();
        return ResponseEntity.ok(bingoCardResponseList);
    }

    @PostMapping("/general")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> generateGeneralBingoCard(@RequestBody GenerateGeneralBingoCardRequest requestBody) {
        BingoCardResponse bingoCardResponse = clueBingoService.generateGeneralBingoCard(requestBody.getItems());
        return ResponseEntity.ok(bingoCardResponse);
    }

    @PutMapping("/loot")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> updateLootCollection(@Valid @RequestBody UpdateLootCollectionRequest updateLootCollectionRequest) {
        clueBingoService.updateLootCollection(
                updateLootCollectionRequest.getOwner(),
                updateLootCollectionRequest.getItem(),
                updateLootCollectionRequest.getAction(),
                updateLootCollectionRequest.getAmount()
        );
        return ResponseEntity.ok("Successfully updated loot collection");
    }

    @GetMapping("/loot")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllLootCollections() {
        List<LootCollectionResponse> lootCollectionResponseList = clueBingoService.getAllLootCollections();
        return ResponseEntity.ok(lootCollectionResponseList);
    }

    @DeleteMapping("/loot")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> resetAllLootCollections() {
        clueBingoService.resetAllLootCollections();
        return ResponseEntity.ok("Loot collections reset");
    }

    @GetMapping("/items")
    public ResponseEntity<?> getAllItems() {
        List<ItemResponse> itemResponseList = clueBingoService.getAllItems();
        return ResponseEntity.ok(itemResponseList);
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
    public ResponseEntity<?> getAllBingoCards() {
        List<BingoCardResponse> bingoCardResponseList = clueBingoService.getAllBingoCards();
        return ResponseEntity.ok(bingoCardResponseList);
    }

    @PostMapping("/{user_id}/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addBingoCardToUser(@PathVariable String user_id) {
        BingoCardResponse bingoCardResponse = clueBingoService.addBingoCardToUser(user_id);
        return ResponseEntity.ok(bingoCardResponse);
    }

    @PostMapping("/registerItems")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerItems() {
        clueBingoService.registerItems();
        return ResponseEntity.ok("Successfully registered items");
    }

    @PostMapping("/registerGeneralBingoCardOwner")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerGeneralBingoCardOwner() {
        clueBingoService.registerGeneralBingoCardOwner();
        return ResponseEntity.ok("Successfully registered general bingo card owner");
    }

    @DeleteMapping("/{bingo_card_id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteBingoCardById(@PathVariable String bingo_card_id) {
        clueBingoService.deleteBingoCardById(bingo_card_id);
        return ResponseEntity.ok("Successfully registered general bingo card owner");
    }

}
