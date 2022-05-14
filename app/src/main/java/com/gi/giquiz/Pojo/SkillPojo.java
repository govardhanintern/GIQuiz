package com.gi.giquiz.Pojo;

import com.google.gson.annotations.SerializedName;

public class SkillPojo {
    @SerializedName("primary_skill_id")
    String primary_skill_id;
    @SerializedName("primary_skill")
    String primary_skill;

    public String getPrimary_skill_id() {
        return primary_skill_id;
    }

    public void setPrimary_skill_id(String primary_skill_id) {
        this.primary_skill_id = primary_skill_id;
    }

    public String getPrimary_skill() {
        return primary_skill;
    }

    public void setPrimary_skill(String primary_skill) {
        this.primary_skill = primary_skill;
    }

    @Override
    public String toString() {
        return "SkillPojo{" +
                "primary_skill_id='" + primary_skill_id + '\'' +
                ", primary_skill='" + primary_skill + '\'' +
                '}';
    }
}
