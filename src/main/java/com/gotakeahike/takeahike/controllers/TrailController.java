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


@RestController
@CrossOrigin(
        origins = {"http://localhost:3000", "http://localhost:8080"},
        allowCredentials = "true",
        exposedHeaders = "*"
)
@RequestMapping("/trail")
public class TrailController {
    private final TrailService trailService;
    public final UserService userService;

    public TrailController(TrailService trailService, UserService userService) {
        this.trailService = trailService;
        this.userService = userService;
    }

    @PostMapping("/addTrail")
    private ResponseEntity<String> addNewTrail(@RequestBody Trail newTrail, @RequestHeader("Authorization") String authorizationHeader) throws Exception {
        try {
            String jwt = authorizationHeader.replace("Bearer ", "");
            JwtTokenProvider.validateToken(jwt);
            Long userId = JwtTokenProvider.extractUserId(jwt);


//            userService.saveTrailToUser(newTrail, userId);
            return ResponseEntity.ok("Trail added successfully");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("/getTrail")
    private ResponseEntity<List<Trail>> getAllTrails() {
        try {
            List<Trail> trails = trailService.findAllTrails();
            return ResponseEntity.ok(trails);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/getAllHikingTrails")
    public ResponseEntity<?> getAllApiTrails() throws IOException, InterruptedException {
          Map<String, TrailAPIDTO> trailData  = trailService.getDataFromApi();
          return ResponseEntity.status(HttpStatus.OK).body(trailData);
    }
}