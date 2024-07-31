package com.gotakeahike.takeahike.services;

import com.gotakeahike.takeahike.Exceptions.UserExistException;
import com.gotakeahike.takeahike.Exceptions.UserNotFoundException;
import com.gotakeahike.takeahike.dto.UserDTO;
import com.gotakeahike.takeahike.models.User;
import com.gotakeahike.takeahike.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void registerUser(User newUser) throws UserExistException {
        Boolean existingUser = userRepository.existsByUsername(newUser.getUsername());
        if (existingUser) {
            System.out.println("User exists");
            throw new UserExistException("Username already exists, please choose another one");
        }
        User user = new User(newUser.getUsername(), newUser.getPassword());
        userRepository.save(user);
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
}