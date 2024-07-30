package com.gotakeahike.takeahike.services;

import com.gotakeahike.takeahike.models.User;
import com.gotakeahike.takeahike.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void registerUser(User newUser) {
        Boolean existingUser = userRepository.existsByUsername(newUser.getUsername());
        if (existingUser) {
            throw new IllegalArgumentException("Username already exists, please choose another one");
        }
        User user = new User(newUser.getUsername(), newUser.getPassword());
        userRepository.save(user);
    }
}
