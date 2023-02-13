package com.markmolenmaker.dioninsanity.backend.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.markmolenmaker.dioninsanity.backend.models.ERole;
import com.markmolenmaker.dioninsanity.backend.models.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(ERole name);
}
