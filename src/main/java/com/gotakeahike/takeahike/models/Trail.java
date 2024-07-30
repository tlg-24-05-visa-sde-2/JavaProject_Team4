package com.gotakeahike.takeahike.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Entity
@Table(name = "trail")
@Getter
@Setter
@NoArgsConstructor
public class Trail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trail_id")
    private Long id;

    @Column
    private Long appId;

    @Column(name = "trail_name")
    private String trailName;

    @Column(name = "trail_length")
    private Double trailLength;

    @Column
    @Enumerated(EnumType.STRING)
    private Experience trailExperience;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Trail(String trailName, Double trailLength, Experience trailExperience, User user) {
        this.trailName = trailName;
        this.trailLength = trailLength;
        this.user = user;
    }
}