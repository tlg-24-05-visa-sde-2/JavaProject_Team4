package com.gotakeahike.takeahike.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gotakeahike.takeahike.dto.TrailDTO;
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


@Service
public class TrailService {
    @Autowired
    private TrailRepository trailRepository;

    public void addNewTrail(Trail newTrail) {
        trailRepository.save(newTrail);
    }

    private final RestTemplate restTemplate;
    public TrailService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${app.trailKey}") // Change this to the actual key with a better name
    private String apiKey;

    public Map<String, TrailDTO> getDataFromApi() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://trailapi-trailapi.p.rapidapi.com/activity/?q-activities_activity_type_name_eq=hiking"))
                .header("x-rapidapi-key", apiKey)
                .header("x-rapidapi-host", "trailapi-trailapi.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, TrailDTO> trailDetailsMap = mapper.readValue(response.body(), mapper.getTypeFactory().constructMapType(Map.class, String.class, TrailDTO.class));
//            System.out.println(trailDetailsMap);
            return trailDetailsMap;
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
//        return restTemplate.getForObject(response.body() <- this is stirng, YOUR-DTO.class);
    }

    public List<Trail> findAllTrails() throws Exception {
        List<Trail> trails = trailRepository.findAll();
        if (trails.isEmpty()) {
            throw new Exception("No trails found");
        }
        return trails;
    }

}