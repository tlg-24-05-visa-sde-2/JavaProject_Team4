package com.gotakeahike.takeahike.controllers;

import com.gotakeahike.takeahike.Exceptions.UserExistException;
import com.gotakeahike.takeahike.Exceptions.UserNotFoundException;
import com.gotakeahike.takeahike.dto.LoginDTO;
import com.gotakeahike.takeahike.dto.UserDTO;
import com.gotakeahike.takeahike.models.User;
import com.gotakeahike.takeahike.services.JwtTokenProvider;
import com.gotakeahike.takeahike.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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
    private ResponseEntity<String> signup(@RequestBody User newUser) throws UserExistException {
        userService.registerUser(newUser);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    private ResponseEntity<?> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response) {
        userService.login(loginDTO, request, response);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully logged in");
    }

    @GetMapping("/getUser")
    private ResponseEntity<UserDTO> getUser(@CookieValue("HikeCookie") String jwtToken) throws Exception {
        JwtTokenProvider.validateToken(jwtToken);
        Long userId = JwtTokenProvider.extractUserId(jwtToken);

        UserDTO user = userService.getUser(userId);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}