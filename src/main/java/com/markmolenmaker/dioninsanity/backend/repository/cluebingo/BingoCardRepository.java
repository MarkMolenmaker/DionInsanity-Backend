package com.markmolenmaker.dioninsanity.backend.repository.cluebingo;

import com.markmolenmaker.dioninsanity.backend.models.User;
import com.markmolenmaker.dioninsanity.backend.models.cluebingo.BingoCard;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BingoCardRepository extends MongoRepository<BingoCard, String> {
    Optional<BingoCard> findByOwner(User owner);

    List<BingoCard> findAllByOwner(User owner);

    boolean existsByOwner(User owner);
}
