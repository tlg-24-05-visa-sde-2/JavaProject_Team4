package com.gotakeahike.takeahike.dto;

import com.gotakeahike.takeahike.models.Experience;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TrailDTO is a Data Transfer Object (DTO) used to encapsulate information about the users hiking trails.
 * This class is used to transfer trail data from the API into the users Saved or Favorite Trails.
 * <p>
 * This DTO was used in service and controller layers to pass that saved trail information.
 */

@Data // Lombok @Data annotation generates getters and setters, toString(), equals(), and hashCode() methods
@NoArgsConstructor // Lombok's annotation to generate a no-argument constructor
@AllArgsConstructor // Lombok's annotation to generate a constructor with all arguments
public class TrailDTO {
    private Long trailId;
    private Long appId;
    private String name;
    private Double trailLength;
    private Experience experience;
}