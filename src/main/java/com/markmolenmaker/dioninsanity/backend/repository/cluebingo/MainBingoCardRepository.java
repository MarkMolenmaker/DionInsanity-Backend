package com.markmolenmaker.dioninsanity.backend.repository.cluebingo;

import com.markmolenmaker.dioninsanity.backend.models.cluebingo.MainBingoCard;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MainBingoCardRepository extends MongoRepository<MainBingoCard, String> {
    <Optional>MainBingoCard findByName(String name);
    boolean existsByName(String name);
}
