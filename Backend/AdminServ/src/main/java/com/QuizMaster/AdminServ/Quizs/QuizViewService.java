package com.QuizMaster.AdminServ.Quizs;

import com.QuizMaster.AdminServ.DBCalls.PreviousQuestionProjection;
import com.QuizMaster.AdminServ.DBCalls.QuestionRepository;
import com.QuizMaster.AdminServ.DBCalls.QuizRepository;
import com.QuizMaster.AdminServ.DBCalls.QuizServiceDB;
import com.QuizMaster.AdminServ.DTO.QuestionPreviousDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizViewService {

    @Autowired
    QuizServiceDB quizServiceDB;
    @Autowired
    QuizRepository qr;
    public List<Quiz> view(){
        return qr.loadTen();
    }

    @Autowired
    QuestionRepository questionRepository;
public List<QuestionPreviousDTO> loadAttemptedQuestions(Long attempID){
    List<PreviousQuestionProjection> a= questionRepository.loadQuestions(attempID);
    List<QuestionPreviousDTO> b=new ArrayList<>();
    for(PreviousQuestionProjection i:a){
        QuestionPreviousDTO c= new QuestionPreviousDTO(
                                    //    pQuestionPreviousDTO(
                  i.getQuestionid(),         //    long questionID,
                  i.getTopicId(),            //    String topicId,
                  i.getQuestion(),            //    String question,
                  i.getOption1(),            //    String option1,
                  i.getOption2(),            //    String option2,
                  i.getOption3(),            //    String option3,
                  i.getOption4(),             //    String option4,
                  i.getIsmcq(),              //    Boolean isMCQ,
                  i.getCorrect(),            //    Integer correct,
                  i.getSeed()               //    Integer seed) {
        );
    }
    return b;
}

}
