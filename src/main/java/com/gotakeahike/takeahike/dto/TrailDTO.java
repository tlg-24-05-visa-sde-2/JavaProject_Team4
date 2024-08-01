package com.gotakeahike.takeahike.dto;

import com.gotakeahike.takeahike.models.Experience;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrailDTO {
    private Long trailId;
    private Long appId;
    private String name;
    private Double trailLength;
    private Experience experience;
}