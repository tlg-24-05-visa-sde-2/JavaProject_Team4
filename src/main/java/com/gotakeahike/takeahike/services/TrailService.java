package com.gotakeahike.takeahike.services;

import com.gotakeahike.takeahike.models.Trail;
import com.gotakeahike.takeahike.repositories.TrailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class TrailService {
    @Autowired
    private TrailRepository trailRepository;

    public void addNewTrail(Trail newTrail) {
        trailRepository.save(newTrail);
    }

    public List<Trail> findAllTrails() throws Exception {
        List<Trail> trails = trailRepository.findAll();
        if (trails.isEmpty()) {
            throw new Exception("No trails found");
        }
        return trails;
    }

    //TODO: Trail API findTrailByID
}