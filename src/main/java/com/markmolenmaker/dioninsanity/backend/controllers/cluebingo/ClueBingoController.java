package com.markmolenmaker.dioninsanity.backend.controllers.cluebingo;

import com.markmolenmaker.dioninsanity.backend.application.cluebingo.ClueBingoService;
import com.markmolenmaker.dioninsanity.backend.payload.response.cluebingo.BingoCardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/cluebingo")
public class ClueBingoController {

    @Autowired
    ClueBingoService clueBingoService;

    @GetMapping("/main")
    public ResponseEntity<?> getMainBingoCard() {
        BingoCardResponse bingoCardResponse = clueBingoService.getMainBingoCard();
        return ResponseEntity.ok(bingoCardResponse);
    }

    @PostMapping("/main")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createMainBingoCard() {
        BingoCardResponse bingoCardResponse = clueBingoService.createMainBingoCard();
        return ResponseEntity.ok(bingoCardResponse);
    }

}
