package com.gotakeahike.takeahike.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Entity class representing a User in the system.
 * Maps to the "app_user" table in the database.
 * Implements UserDetails to integrate with Spring Security.
 */

@Entity //- @Entity: Marks this class as a JPA entity to be mapped to a database table.
@Table(name = "app_user") // - @Table(name = "app_user"): Specifies the table name in the database.
@Getter @Setter //@Getter and @Setter: Lombok annotations to automatically generate getters and setters for the fields.
@NoArgsConstructor //- @NoArgsConstructor: Lombok annotation to generate a no-argument constructor.
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column
    private String password;

    @Column
    private String city;

    @Column
    private String state;

    @Column
    @Enumerated(EnumType.STRING)
    private Experience experience;

    /**
     * List of trails favorited by the user.
     * A user can have multiple favorited trails.
     * The list is eagerly fetched and cascades upon removal.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Trail> favoritedTrails = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Returns the password of the user. Implemented from UserDetails.
     * @return the user's password.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Returns the username of the user. Implemented from UserDetails.
     * @return the user's username.
     */
    @Override
    public String getUsername() {
        return username;
    }


    /**
     * Returns a string representation of the User object.
     * Includes details about the user's favorited trails.
     * @return string representation of the user.
     */
    @Override
    public String toString() {
        StringBuilder trailsString = new StringBuilder("[");
        for (Trail trail : favoritedTrails) {
            trailsString.append(trail.toString()).append(", ");
        }
        if (trailsString.length() > 1) {
            trailsString.setLength(trailsString.length() - 2); // Remove the trailing ", "
        }
        trailsString.append("]");

        return "User: " +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", experience=" + experience +
                ", favoritedTrails=" + trailsString;
    }

    /**
     * Returns a collection of authorities granted to the user.
     * Currently not implemented, so returns null.
     * @return null, as authorities are not used in this implementation.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}