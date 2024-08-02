package com.gotakeahike.takeahike.dto;

import com.gotakeahike.takeahike.models.Trail;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * UserDTO is a Data Transfer Object (DTO) used to encapsulate information about a user.
 * This class is used to transfer user data between different layers of the application.
 * <p>
 * The constructor converts a list of Trail objects to a list of TrailDTO objects.
 */

@Data // Lombok @Data annotation generates getters and setters, toString(), equals(), and hashCode() methods
@NoArgsConstructor // Lombok's annotation to generate a no-argument constructor
public class UserDTO {
    private Long id;
    private String username;
    private List<TrailDTO> trails;

    public UserDTO(Long id, String username, List<Trail> trails) {
        this.id = id;
        this.username = username;
        /*
         * trails.stream(): Creates a stream from the trails list.
         *
         * .map(...): Transforms each Trail object to a TrailDTO object.
         *
         * .collect(Collectors.toList()): Collects the transformed TrailDTO objects into a list and
         * assigns it to this.trails.
         *
         */
        this.trails = trails.stream()
                .map(trail -> new TrailDTO(
                        trail.getId(),
                        trail.getAppId(),
                        trail.getTrailName(),
                        trail.getTrailLength(),
                        trail.getTrailExperience()
                )).collect(Collectors.toList());
    }
}