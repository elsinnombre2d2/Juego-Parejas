package com.example.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.mapper.ScoreMapper;
import com.example.backend.model.Score;

import jakarta.validation.Valid;

@Service
public class ScoreService {
    @Autowired
    private ScoreMapper scoreMapper;


    public List<Score> getTopScores() {
        return scoreMapper.getTopScores();
    }

    public List<Score> findAll() {
        return scoreMapper.findAll();
    }
    public int getPosition(Long id) {
        return scoreMapper.getPosition(id);
    }
    public Score findById(Long id) {
        return scoreMapper.findById(id);
    }
    public Score insert(Score score) {
        scoreMapper.insert(score);
        return score;
    }
    public void update(@Valid Score score) {
        scoreMapper.update(score);
        
    }
    public boolean delete(Long id) {
        scoreMapper.delete(id);
        return true;

        
    }

}
