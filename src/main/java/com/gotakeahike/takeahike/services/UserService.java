package com.gotakeahike.takeahike.services;

import com.gotakeahike.takeahike.Exceptions.UserNotFoundException;
import com.gotakeahike.takeahike.dto.TrailDTO;
import com.gotakeahike.takeahike.dto.UserDTO;
import com.gotakeahike.takeahike.models.User;
import com.gotakeahike.takeahike.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO getUser(Long userId) throws UserNotFoundException {
        User user = userRepository.getById(userId);
        if(user == null) {
            throw new UserNotFoundException("Cannot find user with this ID");
        }

        UserDTO userData = new UserDTO(user.getUsername(), user.getFavoritedTrails());

        System.out.println(user);

        return userData;
    }

    public void saveTrailToUser(TrailDTO trailDTO, Long userid) {

    }
}