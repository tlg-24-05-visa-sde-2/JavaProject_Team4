package com.gotakeahike.takeahike.services;

import com.gotakeahike.takeahike.Exceptions.TrailNotFoundException;
import com.gotakeahike.takeahike.Exceptions.UserNotFoundException;
import com.gotakeahike.takeahike.dto.LoginDTO;
import com.gotakeahike.takeahike.dto.TrailAPIDTO;
import com.gotakeahike.takeahike.dto.TrailDTO;
import com.gotakeahike.takeahike.dto.UserDTO;
import com.gotakeahike.takeahike.models.Experience;
import com.gotakeahike.takeahike.repositories.TrailRepository;
import com.gotakeahike.takeahike.models.Trail;
import com.gotakeahike.takeahike.models.User;
import com.gotakeahike.takeahike.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TrailRepository trailRepository;


    @Autowired
    public UserService(UserRepository userRepository, TrailRepository trailRepository) {
        this.userRepository = userRepository;
        this.trailRepository = trailRepository;
    }

    public UserDTO getUser(Long userId) throws UserNotFoundException {
        User user = userRepository.getById(userId);
        if (user == null) {
            throw new UserNotFoundException("Cannot find user with this ID");
        }

        UserDTO userData = new UserDTO(user.getId(), user.getUsername(), user.getFavoritedTrails());

        System.out.println(user);

        return userData;
    }

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
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Cannot find user with this ID: " + userId));

        Trail trail = trailRepository.findById(trailId)
                .orElseThrow(() -> new TrailNotFoundException("Cannot find trail with this ID: " + trailId));

        user.getFavoritedTrails().remove(trail);
        trailRepository.delete(trail);

        userRepository.save(user);
    }
}