package com.QuizMaster.AdminServ.Questions;

import com.QuizMaster.AdminServ.DBCalls.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionViewService {

    @Autowired
    QuestionRepository qr;
        public List<Question> getQOn(String topicID){
            return qr.getQuestionOn(topicID);
        }

}
