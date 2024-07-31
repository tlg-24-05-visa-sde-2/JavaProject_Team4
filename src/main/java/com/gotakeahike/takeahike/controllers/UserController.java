package com.gotakeahike.takeahike.controllers;

import com.gotakeahike.takeahike.Exceptions.UserExistException;
import com.gotakeahike.takeahike.Exceptions.UserNotFoundException;
import com.gotakeahike.takeahike.dto.UserDTO;
import com.gotakeahike.takeahike.models.User;
import com.gotakeahike.takeahike.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User newUser) throws UserExistException {
        userService.registerUser(newUser);
        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) throws UserNotFoundException {
        UserDTO user = userService.getUser(id);
        System.out.println("Users username: " + user.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}