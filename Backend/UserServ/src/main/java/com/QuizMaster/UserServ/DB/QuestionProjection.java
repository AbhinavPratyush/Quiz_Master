package com.QuizMaster.UserServ.DB;

import com.QuizMaster.UserServ.Job;
import com.QuizMaster.UserServ.ToDo;
@Job(what= """
        Projection class for checking on previous attempt by a user
        """)
public interface QuestionProjection {
    long getQuestionID();
    //    @ForeignKey()
    String getTopicId();
    String getQuestion();
    String getOption1();
    String getOption2();
    String getOption3();
    String getOption4();
    int getSelectedAns();
    int getSeed();



}
