package com.example.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.jpa.ScoreRepository;
import com.example.backend.model.Score;


import jakarta.validation.Valid;

@Service
public class ScoreService {


    @Autowired
    private ScoreRepository scoreRepository;

    public List<Score> getTopScores() {
        return scoreRepository.getTopScores();

    }

    public List<Score> findAll() {
        Iterable<Score> iterableScores = scoreRepository.findAll();
        List<Score> scoreList = new ArrayList<>();
        for (Score score : iterableScores) {
            scoreList.add(score);
        }
        return scoreList;
    }

    public int getPosition(Long id) {
        return scoreRepository.getPosition(id);
    }

    
    public Score findById(Long id) {
        return scoreRepository.findById(id).get();

    }

    public Score insert(@Valid Score score) {
        scoreRepository.save(score);
        return score;
    }

    public void update(@Valid Score score) {
        scoreRepository.save(score);
    }

    public boolean delete(Long id) {
        scoreRepository.delete(scoreRepository.findById(id).get());
        return true;
    }

}
