package com.gotakeahike.takeahike.controllers;

import com.gotakeahike.takeahike.models.Trail;
import com.gotakeahike.takeahike.services.TrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(
        origins = "http://localhost:3000",
        allowCredentials = "true",
        exposedHeaders = "*"
)
@RequestMapping("/trail")
public class TrailController {
    private final TrailService trailService;


    public TrailController(TrailService trailService) {
        this.trailService = trailService;
    }

    @PostMapping("/addTrail")
    public ResponseEntity<String> addNewTrail(@RequestBody Trail newTrail) throws Exception {
        try {
            trailService.addNewTrail(newTrail);
            return ResponseEntity.ok("Trail added successfully");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("/getTrail")
    public ResponseEntity<List<Trail>> getAllTrails() {
        try {
            List<Trail> trails = trailService.findAllTrails();
            return ResponseEntity.ok(trails);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
