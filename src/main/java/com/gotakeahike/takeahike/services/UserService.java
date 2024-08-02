package com.gotakeahike.takeahike.services;

import com.gotakeahike.takeahike.Exceptions.TrailNotFoundException;
import com.gotakeahike.takeahike.Exceptions.UserNotFoundException;
import com.gotakeahike.takeahike.dto.TrailDTO;
import com.gotakeahike.takeahike.dto.UserDTO;
import com.gotakeahike.takeahike.models.Experience;
import com.gotakeahike.takeahike.repositories.TrailRepository;
import com.gotakeahike.takeahike.models.Trail;
import com.gotakeahike.takeahike.models.User;
import com.gotakeahike.takeahike.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing user-related operations such as retrieving user data,
 * saving trails to users, and removing trails from users.
 *
 * Annotations:
 * - @Service: Marks this class as a Spring service component, making it eligible for
 *   component scanning and dependency injection.
 */

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TrailRepository trailRepository;

    /**
     * Constructor to inject dependencies for UserRepository and TrailRepository.
     *
     * @param userRepository - Repository for user data access.
     * @param trailRepository - Repository for trail data access.
     */
    @Autowired
    public UserService(UserRepository userRepository, TrailRepository trailRepository) {
        this.userRepository = userRepository;
        this.trailRepository = trailRepository;
    }

    /**
     * Retrieves a UserDTO object based on the provided user ID.
     * Throws UserNotFoundException if the user cannot be found.
     *
     * @param userId - The ID of the user to retrieve.
     * @return UserDTO - Data transfer object containing user information.
     * @throws UserNotFoundException - If the user with the given ID is not found.
     */
    public UserDTO getUser(Long userId) throws UserNotFoundException {
        // Create UserDTO with the user's ID, username, and favorited trails
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Cannot find user with this ID"));
      
        UserDTO userData = new UserDTO(user.getId(), user.getUsername(), user.getFavoritedTrails());
       
        return userData;
    }

    /**
     * Saves a new trail to a user's list of favorite trails.
     * Throws UserNotFoundException if the user cannot be found.
     *
     * @param trailDTO - Data transfer object containing trail information.
     * @param userId - The ID of the user to whom the trail will be added.
     * @throws UserNotFoundException - If the user with the given ID is not found.
     */
    public void saveTrailToUser(TrailDTO trailDTO, Long userId) throws UserNotFoundException {
        // Retrieve the user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Cannot find user with this ID"));

        // Set Experience(Enum)
        Experience experience;
        if (trailDTO.getTrailLength() < 6) {
            experience = Experience.BEGINNER;
        } else if (trailDTO.getTrailLength() < 12) {
            experience = Experience.INTERMEDIATE;
        } else {
            experience = Experience.ADVANCED;
        }

        System.out.println("trail Length " + trailDTO.getTrailLength());

        // Create and save the new Trail
        Trail trail = new Trail();
        trail.setAppId(trailDTO.getAppId());
        trail.setTrailName(trailDTO.getName());
        trail.setTrailLength(trailDTO.getTrailLength());
        trail.setTrailExperience(experience);

        trail.setUser(user);

        Trail savedTrail = trailRepository.save(trail);

        // Add the new trail to the user's favorite trails
        user.getFavoritedTrails().add(savedTrail);

        // Save the updated user
        userRepository.save(user);
    }

    public void removeTrailFromUser(Long trailId, Long userId) throws UserNotFoundException, TrailNotFoundException {
        // Retrieve the user and trail objects
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Cannot find user with this ID: " + userId));

        Trail trail = trailRepository.findById(trailId)
                .orElseThrow(() -> new TrailNotFoundException("Cannot find trail with this ID: " + trailId));

        // Remove the trail from the user's list of favorited trails and delete the trail
        user.getFavoritedTrails().remove(trail);
        trailRepository.delete(trail);

        // Save the updated user
        userRepository.save(user);
    }
}