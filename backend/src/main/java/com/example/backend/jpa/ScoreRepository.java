package com.example.backend.jpa;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.example.backend.model.Score;

public interface ScoreRepository  extends CrudRepository<Score,Long>{

    @Query("SELECT (SELECT COUNT(s2) " +
           "            FROM Score s2 " +
           "            WHERE s2.moves < s.moves OR (s2.moves = s.moves AND s2.id <= s.id) " +
           "           ) AS position " +
           "FROM Score s " +
           "WHERE s.id = :id")
    int getPosition(@Param("id") Long id);

    @Query("SELECT s FROM Score s ORDER BY s.moves ASC LIMIT 10")
    List<Score> getTopScores();
    
}
