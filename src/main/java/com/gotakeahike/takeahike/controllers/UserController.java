package com.gotakeahike.takeahike.controllers;

import com.gotakeahike.takeahike.Exceptions.UserExistException;
import com.gotakeahike.takeahike.Exceptions.UserNotFoundException;
import com.gotakeahike.takeahike.models.User;
import com.gotakeahike.takeahike.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup/{id}")
    private ResponseEntity<String> signup(@RequestBody User newUser, @PathVariable Long id) throws UserExistException {
        userService.registerUser(newUser);
        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("/getUser/{id}")
    private ResponseEntity<User> getUser(@PathVariable Long id) throws UserNotFoundException {
        User user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }
}