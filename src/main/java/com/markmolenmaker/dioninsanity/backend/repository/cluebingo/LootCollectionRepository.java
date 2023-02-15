package com.markmolenmaker.dioninsanity.backend.repository.cluebingo;

import com.markmolenmaker.dioninsanity.backend.models.User;
import com.markmolenmaker.dioninsanity.backend.models.cluebingo.LootCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LootCollectionRepository extends MongoRepository<LootCollection, String> {

    <Optional> LootCollection findByOwner(User owner);

    boolean existsByOwner(User owner);
}
