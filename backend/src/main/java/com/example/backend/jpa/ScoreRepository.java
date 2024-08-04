package com.example.backend.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.backend.model.Score;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class ScoreRepository {

    @PersistenceContext
    EntityManager entityManager;


    public Score findById(Long id){
        Score score=(Score)     entityManager.find(Score.class,id);
        return score;
    }
    public List<Score> findAll(){
        TypedQuery<Score> query = entityManager.createQuery("SELECT s FROM Score s",Score.class);
        return query.getResultList();
    }
    @Transactional
    public Score save(Score score){
        entityManager.persist(score);
        return score;
    }
    @Transactional
    public Score update(Score score){
        entityManager.merge(score);
        return score;
    }
    @Transactional
    public Score delete(long id){
        Score score = findById(id);
        if(score!=null)
            entityManager.remove(findById(id));
        return score;
    }

    public int getPosition(Long id){
        Query query = entityManager.createQuery(    "SELECT (SELECT COUNT(s2) " +
        "            FROM Score s2 " +
        "            WHERE s2.moves < s.moves OR (s2.moves = s.moves AND s2.id <= s.id) " +
        "           ) AS position " +
        "FROM Score s " +
        "WHERE s.id = :id");
        query.setParameter("id",id);
        return ((Long) query.getSingleResult()).intValue();
    }


    public List<Score> getTopScores(){
        TypedQuery<Score> query = entityManager.createQuery("SELECT s FROM Score s ORDER BY s.moves ASC LIMIT 10",Score.class);
        return query.getResultList();
    }
    
}
