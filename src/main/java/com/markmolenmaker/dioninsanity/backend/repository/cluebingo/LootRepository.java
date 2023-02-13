package com.markmolenmaker.dioninsanity.backend.repository.cluebingo;

import com.markmolenmaker.dioninsanity.backend.models.cluebingo.Loot;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LootRepository extends MongoRepository<Loot, String> {
    <Optional>Loot findByName(String name);
}
