package com.gotakeahike.takeahike.controllers;

import com.gotakeahike.takeahike.dto.UserDTO;
import com.gotakeahike.takeahike.services.JwtTokenProvider;
import com.gotakeahike.takeahike.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(
        origins = {"http://localhost:3000", "http://localhost:8080"},
        allowCredentials = "true",
        exposedHeaders = "*"
)
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/getUser")
    private ResponseEntity<UserDTO> getUser(@CookieValue("HikeCookie") String jwtToken) throws Exception {
        JwtTokenProvider.validateToken(jwtToken);
        Long userId = JwtTokenProvider.extractUserId(jwtToken);

        UserDTO user = userService.getUser(userId);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}