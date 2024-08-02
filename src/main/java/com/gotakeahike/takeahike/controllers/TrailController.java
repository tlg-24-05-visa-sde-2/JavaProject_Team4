package com.gotakeahike.takeahike.controllers;

import com.gotakeahike.takeahike.Exceptions.TrailNotFoundException;
import com.gotakeahike.takeahike.dto.TrailAPIDTO;
import com.gotakeahike.takeahike.models.Trail;
import com.gotakeahike.takeahike.services.TrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * TrailController handles HTTP requests related to trail operations.
 * <p>
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
    private final TrailService trailService;    // Service for handling trail-related operations

    /**
     * Constructor to initialize the {@code TrailService}.
     *
     * @param trailService the {@code TrailService} used to perform trail operations
     */
    @Autowired
    public TrailController(TrailService trailService) { // Constructor to initialize services
        this.trailService = trailService;
    }

    /**
     * Retrieves all trails from the embedded database.
     * <p>
     * This method queries the embedded database for all trail entries and returns them in the response body.
     *
     * @return a {@code ResponseEntity} containing a list of {@code Trail} objects and HTTP status 200 (OK) if successful
     * @throws TrailNotFoundException if no trails are found in the database
     */
    @GetMapping("/getAllEmbeddedTrails")
    private ResponseEntity<List<Trail>> getAllTrails() throws TrailNotFoundException {
            // Retrieve and return all trails from embedded database
            List<Trail> trails = trailService.findAllTrails();
            return ResponseEntity.status(HttpStatus.OK).body(trails);
    }

    /**
     * Retrieves all hiking trails from an external API.
     * <p>
     * This method fetches trail data from an external API and returns it in the response body.
     * The data is mapped to a {@code Map} with trail identifiers as keys and {@code TrailAPIDTO} objects as values.
     *
     * @return a {@code ResponseEntity} containing a {@code Map} of trail identifiers to {@code TrailAPIDTO} objects and HTTP status 200 (OK) if successful
     * @throws IOException if an I/O error occurs while fetching data from the API
     * @throws InterruptedException if the API request is interrupted
     */
    @GetMapping("/getAllHikingTrails")
    private ResponseEntity<?> getAllApiTrails() throws IOException, InterruptedException {
          // Retrieve and return all trails from API data
          Map<String, TrailAPIDTO> trailData  = trailService.getDataFromApi();
          return ResponseEntity.status(HttpStatus.OK).body(trailData);
    }
}