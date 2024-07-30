package com.gotakeahike.takeahike.controllers;

import com.gotakeahike.takeahike.models.User;
import com.gotakeahike.takeahike.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(
        origins = "http://localhost:3000",
        allowCredentials = "true",
        exposedHeaders = "*"
)
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    private ResponseEntity<String> signup(@RequestBody User newUser) throws Exception {
        try {
            userService.registerUser(newUser);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
