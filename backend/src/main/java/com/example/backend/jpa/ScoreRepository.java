package com.example.backend.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


import com.example.backend.model.Score;

public interface ScoreRepository  extends CrudRepository<Score,Long>{

    //int getPosition(Long id);

    //List<Score> getTopScores();
    
}
