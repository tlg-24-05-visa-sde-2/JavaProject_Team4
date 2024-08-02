package com.gotakeahike.takeahike.controllers;

import com.gotakeahike.takeahike.dto.TrailAPIDTO;
import com.gotakeahike.takeahike.models.Trail;
import com.gotakeahike.takeahike.services.JwtTokenProvider;
import com.gotakeahike.takeahike.services.TrailService;
import com.gotakeahike.takeahike.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;


/*
 * TrailController handles HTTP requests related to trail operations.
 *
 * This class:
 * - Provides endpoints for retrieving all trails,
 *   and fetching hiking trails data from an external API.
 * - Uses JWT tokens for authorization to secure endpoints.
 */
@RestController
@CrossOrigin(
        origins = {"http://localhost:3000", "http://localhost:8080"},
        allowCredentials = "true",
        exposedHeaders = "*"
)
@RequestMapping("/trail")
public class TrailController {
    // Service for handling trail-related operations
    private final TrailService trailService;
    // Service for handling user-related operations
    public final UserService userService;

    // Constructor to initialize services
    public TrailController(TrailService trailService, UserService userService) {
        this.trailService = trailService;
        this.userService = userService;
    }

    @PostMapping("/addTrail")
    private ResponseEntity<String> addNewTrail(@RequestBody Trail newTrail, @RequestHeader("Authorization") String authorizationHeader) throws Exception {
        try {
            // Extract JWT token from the Authorization header
            String jwt = authorizationHeader.replace("Bearer ", "");

            // Validate the JWT token
            JwtTokenProvider.validateToken(jwt);
            // Extract user ID from the JWT token
            Long userId = JwtTokenProvider.extractUserId(jwt);

            return ResponseEntity.ok("Trail added successfully");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("/getTrail")
    private ResponseEntity<List<Trail>> getAllTrails() {
        try {
            // Retrieve all trails from the service
            List<Trail> trails = trailService.findAllTrails();
            return ResponseEntity.ok(trails);
        } catch (Exception e) {
            // Handle exceptions and return HTTP status 404 (Not Found)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/getAllHikingTrails")
    public ResponseEntity<?> getAllApiTrails() throws IOException, InterruptedException {
          // Retrieve hiking trail data from an external API
          Map<String, TrailAPIDTO> trailData  = trailService.getDataFromApi();
      
          // Return trail data with HTTP status 200 (OK)
          return ResponseEntity.status(HttpStatus.OK).body(trailData);
    }
}