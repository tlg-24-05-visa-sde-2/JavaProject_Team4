package com.gotakeahike.takeahike.dto;

import com.gotakeahike.takeahike.models.Trail;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private List<TrailDTO> trails;

    public UserDTO(Long id, String username, List<Trail> trails) {
        this.id = id;
        this.username = username;
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
