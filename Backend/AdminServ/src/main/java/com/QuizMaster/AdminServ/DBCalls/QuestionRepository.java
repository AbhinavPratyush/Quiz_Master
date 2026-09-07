package com.QuizMaster.AdminServ.DBCalls;

import com.QuizMaster.AdminServ.Questions.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {

    @Query(value = """
            SELECT
                q.questionid AS questionid,
                q.correct AS correct,
                q.ismcq AS ismcq,
                q.option1 AS option1,
                q.option2 AS option2,
                q.option3 AS option3,
                q.option4 AS option4,
                q.question AS question,
                q.topic_id AS topicId,
                a.seed AS seed
            FROM history h
            JOIN question q
                ON h.questionid = q.questionid
            JOIN attempt a
                ON h.attemptid = a.attemptid
            WHERE h.attemptid = 252
            ORDER BY h.seq ASC;
            
            """,
            nativeQuery = true)
    List<PreviousQuestionProjection> loadQuestions(
            @Param("attemptid") Long attemptID
    );

    @Modifying
    @Query(value = """
            UPDATE
            question
            SET topic_id = 'deleted'
            WHERE questionid =:q;
            """,nativeQuery = true)
    public void stopThisQuestion(
            @Param("q") Long questionID
    );

    @Query(value= """
            SELECT *
            FROM question
            WHERE topic_id= :t;
            """,nativeQuery = true)
    List<Question> getQuestionOn(@Param("t")String topicID);
}
