package com.QuizMaster.UserServ.DB;

import com.QuizMaster.UserServ.DTO.AttemptedQuestionDTO;
import com.QuizMaster.UserServ.Job;
import com.QuizMaster.UserServ.Questions.Question;
import com.QuizMaster.UserServ.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceDB {
    @Autowired
    QuestionRepository questionRepository;

    public List<Question> generateQuestionPool(Long quizID){
        return questionRepository.questionPool(quizID);
    }

    @Job(what = """
            maps Question projection list to An AttemptQuestionDTO lIst 
            """)
    public List<AttemptedQuestionDTO> getPrevQuestions(Long attemptID){
        List<QuestionProjection> lqp=questionRepository.load(attemptID);
        List<AttemptedQuestionDTO>aq=new ArrayList<>();
        for(QuestionProjection qp:lqp){
//            public AttemptedQuestionDTO(long questionID,
//            String topicId,
//            String question,
//            String option1,
//            String option2,
//            String option3,
//            String option4,
//            int selectedAns,
//            int seed) {
                AttemptedQuestionDTO aqdto=new AttemptedQuestionDTO(
                        qp.getQuestionID(),
                        qp.getTopicId(),
                        qp.getQuestion(),
                        qp.getOption1(),
                        qp.getOption2(),
                        qp.getOption3(),
                        qp.getOption4(),
                        qp.getSelectedAns(),
                        qp.getSeed()
                        );
                aq.add(aqdto);
            }
        return aq;
    }
}
