package com.gotakeahike.takeahike.controllers;

import com.gotakeahike.takeahike.dto.TrailDTO;
import com.gotakeahike.takeahike.dto.UserDTO;
import com.gotakeahike.takeahike.services.JwtTokenProvider;
import com.gotakeahike.takeahike.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * UserController handles HTTP requests related to user operations.
 *
 * This class provides endpoints for:
 * - Retrieving user details /getUser.
 * - Adding trails to a user's favorites /saveTrail.
 * - Removing trails from a user's favorites /removeTrail.
 *
 * Authentication and authorization are managed using JWT tokens, which are extracted
 * from cookies. The class interacts with the UserService to perform operations on user data.
 */
@RestController
@CrossOrigin(
        origins = {"http://localhost:3000", "http://localhost:8080"},
        allowCredentials = "true",
        exposedHeaders = "*"
)
@RequestMapping("/user")
public class UserController {
    // Service for handling user-related operations
    private final UserService userService;

    // Constructor to initialize the UserService
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/getUser")
    private ResponseEntity<UserDTO> getUser(@CookieValue("HikeCookie") String jwtToken) throws Exception {
        // Validate the JWT token and extract the user ID
        JwtTokenProvider.validateToken(jwtToken);
        Long userId = JwtTokenProvider.extractUserId(jwtToken);

        // Retrieve the user information from the UserService
        UserDTO user = userService.getUser(userId);

        // Return the user information with HTTP status 200 (OK)
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/saveTrail")
    private ResponseEntity<String> saveTrail(@RequestBody TrailDTO trailDTO, @CookieValue("HikeCookie") String jwtToken) throws Exception {
        // Validate the JWT token and extract the user ID
        JwtTokenProvider.validateToken(jwtToken);
        Long userId = JwtTokenProvider.extractUserId(jwtToken);

        // Save the trail to the user's favorites using the UserService
        userService.saveTrailToUser(trailDTO, userId);

        // Return success message with HTTP status 200 (OK)
        return ResponseEntity.status(HttpStatus.OK).body("Successfully saved trail to user");
    }

    @DeleteMapping("/removeTrail/{trailId}")
    private ResponseEntity<String> removeTrail(@PathVariable Long trailId, @CookieValue("HikeCookie") String jwtToken) throws Exception {
        // Validate the JWT token and extract the user ID
        JwtTokenProvider.validateToken(jwtToken);
        Long userId = JwtTokenProvider.extractUserId(jwtToken);

        // Remove the specified trail from the user's favorites using the UserService
        userService.removeTrailFromUser(trailId, userId);

        // Return success message with HTTP status 200 (OK)
        return ResponseEntity.status(HttpStatus.OK).body("Successfully removed trail from user");
    }
}