package com.gi.programing_quiz.Pojo;

import com.google.gson.annotations.SerializedName;

public class ExperiencePojo {
    @SerializedName("experience_id")
    String experience_id; @SerializedName("experience")
    String experience;

    public String getExperience_id() {
        return experience_id;
    }

    public void setExperience_id(String experience_id) {
        this.experience_id = experience_id;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    @Override
    public String toString() {
        return "ExperiencePojo{" +
                "experience_id='" + experience_id + '\'' +
                ", experience='" + experience + '\'' +
                '}';
    }
}
