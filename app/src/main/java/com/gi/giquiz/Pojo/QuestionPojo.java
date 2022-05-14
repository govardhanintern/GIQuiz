package com.gi.giquiz.Pojo;

import com.google.gson.annotations.SerializedName;

public class QuestionPojo {
    @SerializedName("question_id")
    String question_id;
    @SerializedName("q_question")
    String q_question;
    @SerializedName("optionA")
    String optionA;
    @SerializedName("optionB")
    String optionB;
    @SerializedName("optionC")
    String optionC;
    @SerializedName("optionD")
    String optionD;
    @SerializedName("correct_answer")
    String correct_answer;

    String user_answer = "Z";

    String nonAttempt = "NON";


    public String getNonAttempt() {
        return nonAttempt;
    }

    public void setNonAttempt(String nonAttempt) {
        this.nonAttempt = nonAttempt;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getUser_answer() {
        return user_answer;
    }

    public void setUser_answer(String user_answer) {
        this.user_answer = user_answer;
    }

    public String getQ_question() {
        return q_question;
    }

    public void setQ_question(String q_question) {
        this.q_question = q_question;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    @Override
    public String toString() {
        return "QuestionPojo{" +
                "question_id='" + question_id + '\'' +
                ", q_question='" + q_question + '\'' +
                ", optionA='" + optionA + '\'' +
                ", optionB='" + optionB + '\'' +
                ", optionC='" + optionC + '\'' +
                ", optionD='" + optionD + '\'' +
                ", correct_answer='" + correct_answer + '\'' +
                ", user_answer='" + user_answer + '\'' +
                ", nonAttempt='" + nonAttempt + '\'' +
                '}';
    }
}
