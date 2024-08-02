package com.gotakeahike.takeahike.repositories;

import com.gotakeahike.takeahike.models.Trail;
import com.gotakeahike.takeahike.models.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for accessing and manipulating Trail entities in the database.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 *
 * Annotations and Superclass:
 * - @Repository: (Implicitly used by Spring Data JPA) Marks this interface as a repository for data access.
 * - Extends JpaRepository<Trail, Long>: Provides CRUD operations and additional methods for the Trail entity with Long as the ID type.
 */

public interface TrailRepository extends JpaRepository<Trail, Long> {

    /**
     * Finds all trails associated with a specific user.
     *
     * @param userId - The User entity for which trails are to be found.
     * @return List<Trail> - A list of Trail entities associated with the specified user.
     */
    List<Trail> findByUser(User userId);
    @NonNull
    List<Trail> findAll();
}