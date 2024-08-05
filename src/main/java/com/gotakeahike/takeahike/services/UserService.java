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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing user-related operations such as retrieving user data,
 * saving trails to users, and removing trails from users.
 * <p>
 * Annotations:
 * - @Service: Marks this class as a Spring service component, making it eligible for
 *   component scanning and dependency injection.
 */

@Service
public class UserService {
    private final UserRepository userRepository;

    /*
      * When we import TrailService here, it can no longer use User service.
      *
      * If you tried to import UserService into TrailService it would create
      * a "cyclical dependency". Meaning only one, can rely on the other. Generally, the User/UserService will be
      * the one to have multiple dependencies
      *
      * Another note: we use dependency injection for trailService here because
      * we need to interact with the trail database, however,
      * we should not have access directly to the "TrailRepository" here in the UserService.
     */
    private final TrailService trailService;

    /**
     * Constructor to inject dependencies for UserRepository and TrailRepository.
     *
     * @param userRepository - Repository for user data access.
     * @param trailService - Service for trail data access.
     */
    @Autowired
    public UserService(UserRepository userRepository, TrailService trailService) {
        this.userRepository = userRepository;
        this.trailService = trailService;
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

        return new UserDTO(user.getId(), user.getUsername(), user.getFavoritedTrails());
    }

    /**
     * Saves a new trail to a user's list of favorite trails.
     * Throws UserNotFoundException if the user cannot be found.
     *
     * @param trailDTO - Data transfer object containing trail information.
     * @param userId - The ID of the user to whom the trail will be added.
     * @throws UserNotFoundException - If the user with the given ID is not found.
     */
    @Transactional
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

        trailService.saveTrail(trail);

        // Add the new trail to the user's favorite trails
        user.getFavoritedTrails().add(trail);

        // Save the updated user
        userRepository.save(user);
    }

    /**
     * Removes a trail from a user's list of favorited trails and deletes the trail from the repository.
     * <p>
     * This method performs the following operations:
     * <ul>
     *     <li>Retrieves the {@link User} and {@link Trail} objects based on the provided IDs.</li>
     *     <li>Removes the specified trail from the user's list of favorited trails.</li>
     *     <li>Deletes the trail from the {@link Trail} repository.</li>
     *     <li>Saves the updated {@link User} object to the {@link User} repository.</li>
     * </ul>
     * <p>
     * If the user or trail cannot be found, appropriate exceptions are thrown.
     *
     * @param trailId the ID of the trail to be removed
     * @param userId the ID of the user from whom the trail is to be removed
     * @throws UserNotFoundException if no user with the given ID is found
     * @throws TrailNotFoundException if no trail with the given ID is found
     */
    @Transactional
    public void removeTrailFromUser(Long trailId, Long userId) throws UserNotFoundException, TrailNotFoundException {
        // Retrieve the user and trail objects
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Cannot find user with this ID: " + userId));

        // Remove the trail from the user's list of favorited trails and delete the trail
        List<Trail> favoritedTrails = user.getFavoritedTrails();
        favoritedTrails.removeIf(trail -> trail.getId().equals(trailId));

        trailService.removeTrail(trailId);

        // Save the updated user
        userRepository.save(user);
    }
}