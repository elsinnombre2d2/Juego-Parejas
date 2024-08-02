package com.example.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.jpa.ScoreRepository;
import com.example.backend.mapper.ScoreMapper;
import com.example.backend.model.Score;
import com.example.backend.model.Score;

import jakarta.validation.Valid;

@Service
public class ScoreService {


    @Autowired
    private ScoreRepository scoreRepository;

    public List<Score> getTopScores() {
        //return scoreRepository.getTopScores();
        //return scoreMapper.getTopScores();
        return null;
    }

    public List<Score> findAll() {
        Iterable<Score> iterableScores = scoreRepository.findAll();
        List<Score> scoreList = new ArrayList<>();
        for (Score score : iterableScores) {
            scoreList.add(score);
        }
        return scoreList;

        // return scoreMapper.findAll();
    }

    public int getPosition(Long id) {
        //return scoreRepository.getPosition(id);
        return 0;
        //return scoreMapper.getPosition(id);
    }

    
    public Score findById(Long id) {
        return scoreRepository.findById(id).get();
        //return scoreMapper.findById(id);
    }

    public Score insert(Score score) {
        //scoreMapper.insert(score);
        scoreRepository.save(score);
        return score;
    }

    public void update(@Valid Score score) {
        scoreRepository.save(score);
        //scoreMapper.update(score);

    }

    public boolean delete(Long id) {
        scoreRepository.delete(scoreRepository.findById(id).get());
        //scoreMapper.delete(id);
        return true;

    }

}
