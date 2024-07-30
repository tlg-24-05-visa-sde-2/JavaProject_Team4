package com.gotakeahike.takeahike.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter @Setter
@NoArgsConstructor
public class Trail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "trail_id")
    private Long id;

    @Column
    private Long appId;  //Given from API

    @Column
    private String trailName;

    @Column
    private Double trailLength; //In miles?

    @Column
    private Experience trailExperience;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    //Constructors
    public Trail(String trailName, Double trailLength, Experience trailExperience, User user) {
        this.trailName = trailName;
        this.trailLength = trailLength;
        this.trailExperience = trailExperience;
        this.user = user;
    }
}