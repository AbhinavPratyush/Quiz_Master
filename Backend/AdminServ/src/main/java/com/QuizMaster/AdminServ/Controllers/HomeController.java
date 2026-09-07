package com.QuizMaster.AdminServ.Controllers;

import com.QuizMaster.AdminServ.DBCalls.*;
import com.QuizMaster.AdminServ.DTO.AttemptDTO;
import com.QuizMaster.AdminServ.DTO.QuestionDTO;
import com.QuizMaster.AdminServ.DTO.QuestionPreviousDTO;
import com.QuizMaster.AdminServ.DTO.QuizDTO;
import com.QuizMaster.AdminServ.Questions.Question;
import com.QuizMaster.AdminServ.Questions.QuestionCreationService;
import com.QuizMaster.AdminServ.Questions.QuestionViewService;
import com.QuizMaster.AdminServ.Quizs.Quiz;
import com.QuizMaster.AdminServ.Quizs.QuizCreationService;
import com.QuizMaster.AdminServ.Quizs.QuizViewService;
import com.QuizMaster.AdminServ.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
public class HomeController {

// These are uploading the quiz and questions services
    @Autowired
    QuestionCreationService questionCreationService;
    @PostMapping("/admin/{topicId}")
    public ResponseEntity<Object> addQuestion(@RequestBody QuestionDTO questionDTO, @PathVariable String topicId){
        // possible db error
        //noinspection UnnecessaryLocalVariable
        ResponseEntity<Object> response=questionCreationService.createQuestion(new Question(topicId,questionDTO));
        return response;
    }


    @Autowired
    QuizCreationService quizCreationService;
    @PostMapping("/admin/quiz")
    public ResponseEntity<Object> addQuiz(@RequestBody QuizDTO quizDTO){
        return quizCreationService.createQuiz(quizDTO);
    }

//--------------------------------------------------------------------------------------------------
//These are the viewing and dashboard service

    // to view the questions belonging to the topic
    @ToDo(what = """
            Frontend is not integrated here still.
           """)
    @Autowired
    QuestionViewService qvss;
    @PostMapping("/admin/view/Questions/{topicID}")
    public List<Question> viewQonTopic(String topicID){
        return qvss.getQOn(topicID);
    }

        // The main dashboard to load questions
    @Autowired
    QuizViewService quizViewService;
    @PostMapping("/admin/dashboard")
    public List<Quiz> adminDashboard(){
        return quizViewService.view();
    }


    //To load the attempts in the question
    @Autowired
    AttemptServiceDB attemptServiceDB;
    @PostMapping("/admin/quiz/{quizID}")
    public List<AttemptDTO> attemptsInQuiz(@PathVariable Long quizID){

        return attemptServiceDB.loadAttempts(quizID);
    }

    //To load the questions in that attempt
    @ToDo(what = """
            Frontend is connected to the api,
            but the expected value in frontend differs from the provided value
            """)
    @Autowired
    QuizViewService qvs;
    @PostMapping("/admin/attempt/{attemptID}")
    public List<QuestionPreviousDTO> questionsInThis(@PathVariable Long attemptID){
        return qvs.loadAttemptedQuestions(attemptID);
    }


//--------------------------------------------------------------------------------------------------------
// deleting services

    @ToDo(what = """
            Frontend connection unavailable.
            No API to recieve this
            """)
    @Autowired
    QuizServiceDB quizServiceDB;
    @PostMapping("admin/delete/quiz/{quizID}")
    public void deleteQuiz(@PathVariable Long quizID){
        quizServiceDB.delete(quizID);
    }

    @ToDo(what= """
            Frontend connection unavailable
            """)
    @Autowired
    QuestionServiceDB qsdb;
    @PostMapping("admin/delete/question/{questionID}")
    public void deleteQuestion(@PathVariable Long questionID){
        qsdb.delete(questionID);
    }

}
