package com.gotakeahike.takeahike.repositories;

import com.gotakeahike.takeahike.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Repository interface for accessing and manipulating User entities in the database.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 * Annotations and Superclass:
 * - @Repository: Marks this interface as a repository for data access.
 * - Extends JpaRepository<User, Long>: Provides CRUD operations and additional methods for the User entity with Long as the ID type.
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /*
     * Finds a User entity by its username.
     *
     * @param username - The username of the User entity to be found.
     * @return User - The User entity with the specified username.
     */
    User findByUsername(String username);

    /*
     * Retrieves a User entity by its ID.
     *
     * @param id - The ID of the User entity to be retrieved.
     * @return User - The User entity with the specified ID.
     */
    User getById(Long id);

    /*
     * Checks if a User entity exists with the specified username.
     *
     * @param username - The username to check for existence.
     * @return Boolean - True if a User with the specified username exists, otherwise false.
     */
    User findByUsername(String username);
  
    Boolean existsByUsername(String username);
}