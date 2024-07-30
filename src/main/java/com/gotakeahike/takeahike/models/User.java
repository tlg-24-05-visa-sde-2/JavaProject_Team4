package com.gotakeahike.takeahike.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import static jakarta.persistence.FetchType.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter @Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "user_id")
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String city;

    @Column
    private String state;

    @Column
    private Experience experience;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = EAGER)
    private List<Trail> favoritedTrails = new ArrayList<>();

    //Constructors
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}