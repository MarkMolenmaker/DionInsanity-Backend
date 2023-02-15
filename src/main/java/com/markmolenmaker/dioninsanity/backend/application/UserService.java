package com.markmolenmaker.dioninsanity.backend.application;

import com.markmolenmaker.dioninsanity.backend.application.cluebingo.ClueBingoService;
import com.markmolenmaker.dioninsanity.backend.models.User;
import com.markmolenmaker.dioninsanity.backend.models.cluebingo.BingoCard;
import com.markmolenmaker.dioninsanity.backend.models.cluebingo.LootCollection;
import com.markmolenmaker.dioninsanity.backend.payload.error.UserRegistrationException;
import com.markmolenmaker.dioninsanity.backend.payload.response.UserResponse;
import com.markmolenmaker.dioninsanity.backend.payload.response.cluebingo.BingoCardResponse;
import com.markmolenmaker.dioninsanity.backend.repository.UserRepository;
import com.markmolenmaker.dioninsanity.backend.repository.cluebingo.BingoCardRepository;
import com.markmolenmaker.dioninsanity.backend.repository.cluebingo.LootCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LootCollectionRepository lootCollectionRepository;

    @Autowired
    ClueBingoService clueBingoService;

    public List<UserResponse> getAllUsers() {
        List<UserResponse> userResponseList = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase("general_bingo_card_owner")) continue;
            List<String> roles = user.getRoles().stream().map(
                    role -> role.getName().name()
            ).toList();
            UserResponse userResponse = new UserResponse(
                    user.getId(),
                    user.getTwitchId(),
                    user.getUsername(),
                    user.getDisplayName(),
                    user.getProfileImageUrl(),
                    roles
            );

            // Find the user's Bingo Card
            userResponse.setBingoCards(clueBingoService.getBingoCardsByOwner(user));

            userResponseList.add(userResponse);
        }
        return userResponseList;
    }

    public UserResponse registerUser(String id, String login, String displayName, String profileImageUrl) throws UserRegistrationException {
        if (userRepository.existsByTwitchId(id))
            throw new UserRegistrationException("User with id " + id + " already exists.");
        if (userRepository.existsByUsername(login))
            throw new UserRegistrationException("User with username " + login + " already exists.");

        User user = new User();
        user.setTwitchId(id);
        user.setUsername(login);
        user.setDisplayName(displayName);
        user.setProfileImageUrl(profileImageUrl);

        userRepository.save(user);

        // Create loot collection if it doesn't exist
        if (!lootCollectionRepository.existsByOwner(user)) {
            LootCollection lootCollection = new LootCollection();
            lootCollection.setOwner(user);
            lootCollectionRepository.save(lootCollection);
        }

        return new UserResponse(
                user.getId(),
                user.getTwitchId(),
                user.getUsername(),
                user.getDisplayName(),
                user.getProfileImageUrl(),
                user.getRoles().stream().map(
                        role -> role.getName().name()
                ).toList()
        );
    }
}
