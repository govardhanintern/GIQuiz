package com.gi.programing_quiz.Pojo;

import com.google.gson.annotations.SerializedName;

public class SubjectPojo {
    @SerializedName("subject_image")
    String subject_image;

    @SerializedName("subject_name")
    String subject_name;

     @SerializedName("subject_id")
    String subject_id;

    @Override
    public String toString() {
        return "SubjectPojo{" +
                "subject_image='" + subject_image + '\'' +
                ", subject_name='" + subject_name + '\'' +
                ", subject_id='" + subject_id + '\'' +
                '}';
    }

    public String getSubject_image() {
        return subject_image;
    }

    public void setSubject_image(String subject_image) {
        this.subject_image = subject_image;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }
}
