package com.QuizMaster.AdminServ.DBCalls;

public interface PreviousQuestionProjection {

    Long getQuestionid();

    Integer getCorrect();

    Boolean getIsmcq();

    String getOption1();

    String getOption2();

    String getOption3();

    String getOption4();

    String getQuestion();

    String getTopicId();

    Integer getSeed();
}