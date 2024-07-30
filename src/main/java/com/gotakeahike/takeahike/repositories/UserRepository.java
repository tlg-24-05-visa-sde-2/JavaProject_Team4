package com.gotakeahike.takeahike.repositories;


import com.gotakeahike.takeahike.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User getById(Long id);

    Boolean existsByUsername(String username);

}
