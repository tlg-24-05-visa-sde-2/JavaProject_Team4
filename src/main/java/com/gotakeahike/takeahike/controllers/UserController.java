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
 * <p>
 * This class provides endpoints for:
 * - Retrieving user details /getUser.
 * - Adding trails to a user's favorites /saveTrail.
 * - Removing trails from a user's favorites /removeTrail.
 * <p>
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

    /**
     * Constructs a new {@code UserController} with the specified {@code UserService}.
     *
     * @param userService the service used to handle user-related operations
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieves the user information based on the provided JWT token.
     * Validates the token, extracts the user ID, and fetches the user details from the {@code UserService}.
     *
     * @param jwtToken the JWT token used to authenticate and identify the user
     * @return a {@code ResponseEntity} containing the {@code UserDTO} and HTTP status 200 (OK) if successful
     * @throws Exception if token validation or user retrieval fails
     */
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

    /**
     * Saves a trail to the user's favorites based on the provided trail data and JWT token.
     * Validates the token, extracts the user ID, and saves the trail using the {@code UserService}.
     *
     * @param trailDTO the data transfer object containing trail information
     * @param jwtToken the JWT token used to authenticate and identify the user
     * @return a {@code ResponseEntity} with a success message and HTTP status 200 (OK) if successful
     * @throws Exception if token validation or trail saving fails
     */
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

    /**
     * Removes a trail from the user's favorites based on the provided trail ID and JWT token.
     * Validates the token, extracts the user ID, and removes the trail using the {@code UserService}.
     *
     * @param trailId the ID of the trail to be removed
     * @param jwtToken the JWT token used to authenticate and identify the user
     * @return a {@code ResponseEntity} with a success message and HTTP status 200 (OK) if successful
     * @throws Exception if token validation or trail removal fails
     */
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