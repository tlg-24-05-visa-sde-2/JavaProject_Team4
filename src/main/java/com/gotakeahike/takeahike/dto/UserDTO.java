package com.gotakeahike.takeahike.dto;

import com.gotakeahike.takeahike.models.Trail;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {
    private String username;
    private List<Trail> trails;

    public UserDTO(String username, List<Trail> trails) {
        this.username = username;
        this.trails = trails;
    }
}
