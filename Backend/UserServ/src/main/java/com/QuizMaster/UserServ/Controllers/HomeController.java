package com.QuizMaster.UserServ.Controllers;

import com.QuizMaster.UserServ.Auth.SecurityService;
import com.QuizMaster.UserServ.Auth.User;
import com.QuizMaster.UserServ.Auth.UserRepository;
import com.QuizMaster.UserServ.DB.AttemptRepository;
import com.QuizMaster.UserServ.DB.AttemptServiceDB;
import com.QuizMaster.UserServ.DB.QuestionServiceDB;
import com.QuizMaster.UserServ.DB.QuizRepositories;
import com.QuizMaster.UserServ.DTO.AttemptDTO;
import com.QuizMaster.UserServ.DTO.QuestionDTO;
import com.QuizMaster.UserServ.Job;
import com.QuizMaster.UserServ.Questions.Question;
import com.QuizMaster.UserServ.Quizs.Quiz;
import com.QuizMaster.UserServ.Services.AttemptService;
import com.QuizMaster.UserServ.Services.QuizService;
import com.QuizMaster.UserServ.Services.SavingService;
import com.QuizMaster.UserServ.Services.SendingNextQuestionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class HomeController {

    @Autowired
    QuizService quizService;


    @Job(what = """
            This api is supposed to start the quiz .
            This is supposed to start a chain of questions
            that will count as an attempt.
            """)
    @PostMapping("/start/{quizID}")
    public Long startTheQuiz(@PathVariable Long quizID){
        return quizService.startTheQuiz(quizID);
    }


    @Job(what= """
            This is supposed to continue that Question chain.
            It loads the next question and save the question 
            which has been answered.
            """)
    @Autowired
    SavingService savingService;
    @Autowired
    SendingNextQuestionService sendingNextQuestionService;
    @PostMapping("/quiz/{attemptID}")
    public QuestionDTO theQuiz(@PathVariable Long attemptID,@RequestBody QuestionDTO previousQuestion){
        savingService.saveThis(previousQuestion,attemptID);
        Optional<QuestionDTO> nextQuestion=sendingNextQuestionService.sendNext(attemptID);
        if(nextQuestion.isEmpty())return null;
        return nextQuestion.get();
    }

    @Job(what = """
            This is supposed to load the attempts associated with 
            the quizzes  which has been given. It returns every 
            attempt ever taken by a user.
            """)
    @Autowired
    AttemptServiceDB attemptServiceDB;
    @Autowired
    SecurityService securityService;
    @PostMapping("/user/history")
    public List<AttemptDTO> userAttempts() {
        return attemptServiceDB.loadAttempts(
                securityService.getCurrentUserId()
        );
    }

//---------------------------------------------------------------------------------------------

    @Job(what= """
            This is a simple api called by the frontend 
            at a fixed rate. Now that api is called 
            during the question chain or running attempt.
            That will help to renew the last_activity
            """)
    @Autowired
    AttemptRepository attemptRepository;
    @Transactional
    @GetMapping("/quiz/{attemptID}/heartbeat")
    public void senseHeartbeat(@PathVariable Long attemptID){
        attemptRepository.update(attemptID);
    }

//-----------------------------------------------------------------------------------------------

    //view controlls

    @Job(what = """
            This returns valid quizzes for a given user.
            Validity is sensed by attempt left for that particular user.
            """)
    @Autowired
    QuizRepositories quizRepositories;
    @PostMapping("/user/dashboard")
    public List<Quiz> userDashboard(@AuthenticationPrincipal UserDetails userDetails){
        List<Quiz>quizzes= quizRepositories.loadUpcoming(userDetails.getUsername());

        System.out.println("QUIZZES RETURNED = " + quizzes.size());
        for (Quiz q : quizzes) {
            System.out.println(
                    "Quiz ID: " + q.getQuizID() +
                            ", Topic: " + q.getTopicId() +
                            ", Attempts: " + q.getAttempts()
            );
        }
        return quizzes;
    }


    //auth controller
    @Job(what= """
            Register new users.
            """)
    @Autowired
    UserRepository userRepository;
    @PostMapping("/register")
    public void newUser(@RequestBody User newUser){
        userRepository.save(newUser);
    }



    //---------------Deleting service--------------------------------------------------------
    // user is not allowed to delete any thing

    //
    //-------------------------View the attempted question paper----------------------------

    @Job(what= """
        This is supposed to load the attempted question,
         and the answer selected.
        """)
    @Autowired
    QuestionServiceDB qsdb;
    @PostMapping("/user/attempt/history/{attemptID}")
    public List<AttemptedQuestionDTO> load(@PathVariable Long attemptID){
        return qsdb.getPrevQuestions();
    }


}
