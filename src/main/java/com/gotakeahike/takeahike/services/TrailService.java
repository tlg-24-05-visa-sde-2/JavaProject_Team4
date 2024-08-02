package com.gotakeahike.takeahike.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gotakeahike.takeahike.dto.TrailAPIDTO;
import com.gotakeahike.takeahike.models.Trail;
import com.gotakeahike.takeahike.repositories.TrailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

/**
 * Service class for managing trail-related operations such as fetching trail data from an external API
 * and retrieving trails from the database.
 * <p>
 * Annotations:
 * - @Service: Marks this class as a Spring service component, enabling dependency injection and service layer functionality.
 */
@Service
public class TrailService {
    @Autowired
    private TrailRepository trailRepository;

    private final RestTemplate restTemplate;

    public TrailService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${app.trailKey}") // Injects the API key from application properties
    private String apiKey;

    /**
     * Fetches trail data from an external API.
     * Parses the response JSON into a Map where the key is a String and the value is a TrailAPIDTO.
     *
     * @return Map<String, TrailAPIDTO> - A map of trail data from the API.
     * @throws IOException          - If an I/O error occurs while reading the API response.
     * @throws InterruptedException - If the thread is interrupted while waiting for the API response.
     */
    public Map<String, TrailAPIDTO> getDataFromApi() throws IOException, InterruptedException {
        // Create an HTTP request to the external API
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://trailapi-trailapi.p.rapidapi.com/activity/?q-activities_activity_type_name_eq=hiking"))
                .header("x-rapidapi-key", apiKey)
                .header("x-rapidapi-host", "trailapi-trailapi.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        // Send the request and receive the response
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // Create an ObjectMapper to parse the JSON response
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Convert the JSON response body into a Map of TrailAPIDTO objects
            return mapper.readValue(response.body(), mapper.getTypeFactory().constructMapType(Map.class, String.class, TrailAPIDTO.class));
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Retrieves all trails from the database.
     * Throws an exception if no trails are found.
     *
     * @return List<Trail> - A list of all trails from the database.
     * @throws Exception - If no trails are found in the database.
     */
    public List<Trail> findAllTrails() throws Exception {
        List<Trail> trails = trailRepository.findAll();
        if (trails.isEmpty()) {
            throw new Exception("No trails found");
        } else {
            return trails;
        }
    }
}