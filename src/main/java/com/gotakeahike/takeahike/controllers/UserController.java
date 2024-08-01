package com.gotakeahike.takeahike.controllers;

import com.gotakeahike.takeahike.Exceptions.UserExistException;
import com.gotakeahike.takeahike.dto.LoginDTO;
import com.gotakeahike.takeahike.dto.TrailAPIDTO;
import com.gotakeahike.takeahike.dto.TrailDTO;
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

    @PostMapping("/saveTrail")
    private ResponseEntity<String> saveTrail(@RequestBody TrailDTO trailDTO, @CookieValue("HikeCookie") String jwtToken) throws Exception {
        JwtTokenProvider.validateToken(jwtToken);
        Long userId = JwtTokenProvider.extractUserId(jwtToken);

        userService.saveTrailToUser(trailDTO, userId);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully saved trail to user");
    }
}