package com.QuizMaster.AdminServ.DBCalls;

import com.QuizMaster.AdminServ.Quizs.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Long> {


    @Query(value="SELECT" +
            "    q.*," +
            "    COUNT(a.attemptid) AS attempt_count" +
            " FROM quiz q " +
            "LEFT JOIN attempt a " +
            "    ON q.quizid = a.quizid " +
            "GROUP BY q.quizid " +
            "ORDER BY attempt_count ASC " +
            "LIMIT 10;",
            nativeQuery=true)
    List<Quiz> loadTen();

    @Modifying
    @Query(value= """
            UPDATE quiz
            SET attempts = 0
            WHERE quizid = :q;
            """, nativeQuery = true)
    void stopThisQuiz(
            @Param("q") Long quizid
    );

}
