package com.markmolenmaker.dioninsanity.backend.controllers.cluebingo;

import com.markmolenmaker.dioninsanity.backend.application.cluebingo.ClueBingoService;
import com.markmolenmaker.dioninsanity.backend.payload.request.cluebingo.GenerateGeneralBingoCardRequest;
import com.markmolenmaker.dioninsanity.backend.payload.request.cluebingo.UpdateLootCollectionRequest;
import com.markmolenmaker.dioninsanity.backend.payload.response.cluebingo.BingoCardResponse;
import com.markmolenmaker.dioninsanity.backend.payload.response.cluebingo.ItemResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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

    @PostMapping("/general")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> generateGeneralBingoCard(@RequestBody GenerateGeneralBingoCardRequest requestBody) {
        BingoCardResponse bingoCardResponse = clueBingoService.generateGeneralBingoCard(requestBody.getItems());
        return ResponseEntity.ok(bingoCardResponse);
    }

    @PutMapping("/loot")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> updateLootCollection(@Valid @RequestBody UpdateLootCollectionRequest updateLootCollectionRequest) {
        BingoCardResponse bingoCardResponse = clueBingoService.updateLootCollection(
                updateLootCollectionRequest.getBingoCardId(),
                updateLootCollectionRequest.getOwner(),
                updateLootCollectionRequest.getItem(),
                updateLootCollectionRequest.getAction(),
                updateLootCollectionRequest.getAmount()
        );
        return ResponseEntity.ok(bingoCardResponse);
    }

    @GetMapping("/items")
    public ResponseEntity<?> getAllItems() {
        List<ItemResponse> itemResponseList = clueBingoService.getAllItems();
        return ResponseEntity.ok(itemResponseList);
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

}
