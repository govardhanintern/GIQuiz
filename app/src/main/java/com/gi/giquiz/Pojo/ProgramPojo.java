package com.gi.giquiz.Pojo;

import com.google.gson.annotations.SerializedName;

public class ProgramPojo {
    @SerializedName("pList_id")
    String pList_id;
    @SerializedName("p_question")
    String p_question;
    @SerializedName("solution")
    String solution;
    @SerializedName("status")
    String status;

    public String getpList_id() {
        return pList_id;
    }

    public void setpList_id(String pList_id) {
        this.pList_id = pList_id;
    }

    public String getP_question() {
        return p_question;
    }

    public void setP_question(String p_question) {
        this.p_question = p_question;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ProgramPojo{" +
                "pList_id='" + pList_id + '\'' +
                ", p_question='" + p_question + '\'' +
                ", solution='" + solution + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
