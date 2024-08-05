package com.gotakeahike.takeahike.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gotakeahike.takeahike.Exceptions.TrailNotFoundException;
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
    @Value("${app.trailKey}") // Injects the API key from application properties
    private String apiKey;
    private final TrailRepository trailRepository;
    @Autowired
    public TrailService(TrailRepository trailRepository) {
        this.trailRepository = trailRepository;
    }

    /**
     * Fetches trail data from an external API.
     * Parses the response JSON into a Map where the key is a String and the value is a TrailAPIDTO.
     *
     * @return Map&lt;String, TrailAPIDTO&gt; - A map of trail data from the API.
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
     * @return List&lt;Trail&gt; - A list of all trails from the database.
     */
    public List<Trail> findAllTrails() throws TrailNotFoundException {
        List<Trail> trails = trailRepository.findAll();
        if (trails.isEmpty()) {
            throw new TrailNotFoundException("No trails found");
        } else {
            return trails;
        }
    }

    public void removeTrail(Long trailId) throws TrailNotFoundException {
       Trail trail = trailRepository.findById(trailId)
               .orElseThrow(() -> new TrailNotFoundException("Cannot find trial with ID of " + trailId)) ;
       trailRepository.delete(trail);
    }

    public void saveTrail(Trail trail) {
        trailRepository.save(trail);
    }
}