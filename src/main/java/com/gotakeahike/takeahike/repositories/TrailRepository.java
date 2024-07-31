package com.gotakeahike.takeahike.repositories;

import com.gotakeahike.takeahike.models.Trail;
import com.gotakeahike.takeahike.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrailRepository extends JpaRepository<Trail, Long> {

    List<Trail> findByUser(Long userId);

    Trail getById(Long id);
}