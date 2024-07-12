package com.example.backend.mapper;

import com.example.backend.model.Score;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ScoreMapper {

    @Select("SELECT * from Scores")
    List<Score> findAll();

    @Select("SELECT * from Scores WHERE ID=#{id}")
    Score findById(Long id);

    @Insert("INSERT INTO scores(userName, team, moves,time) VALUES(#{userName}, #{team},#{moves},#{time})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Score score);

    @Select("SELECT * FROM scores ORDER BY moves ASC LIMIT 10")
    List<Score> getTopScores();

    @Select("SELECT \r\n" + //
            "    (SELECT COUNT(*)\r\n" + //
            "     FROM scores s2\r\n" + //
            "     WHERE s2.moves < s.moves OR (s2.moves = s.moves AND s2.id <= s.id)\r\n" + //
            "    ) AS position\r\n" + //
            "FROM scores s\r\n" + //
            "WHERE id = #{id}")
    int getPosition(Long id);

    @Update("UPDATE scores " +
            " SET userName = #{userName}, team = #{team}, moves = #{moves}, time = #{time} " +
            " WHERE id = #{id}")
    void update(Score score);

    @Delete("DELETE FROM scores WHERE id = #{id}")
    void delete(Long id);
}
