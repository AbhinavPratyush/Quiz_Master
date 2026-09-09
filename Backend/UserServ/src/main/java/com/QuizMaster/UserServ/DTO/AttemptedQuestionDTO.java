package com.QuizMaster.UserServ.DTO;

import com.QuizMaster.UserServ.ToDo;

@ToDo(what = """
        Pickup from question projection every thing
        """)
public class AttemptedQuestionDTO {
    long questionID;
    String topicId;
    String question;
    String option1;
    String option2;
    String option3;
    String option4;
    int selectedAns;
    int seed;

    public AttemptedQuestionDTO() {
    }


    public AttemptedQuestionDTO(long questionID, String topicId, String question, String option1, String option2, String option3, String option4, int selectedAns, int seed) {
        this.questionID = questionID;
        this.topicId = topicId;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.selectedAns = selectedAns;
        this.seed = seed;
    }

    public long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(long questionID) {
        this.questionID = questionID;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public int getSelectedAns() {
        return selectedAns;
    }

    public void setSelectedAns(int selectedAns) {
        this.selectedAns = selectedAns;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }
}
