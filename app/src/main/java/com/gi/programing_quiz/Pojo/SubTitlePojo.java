package com.gi.programing_quiz.Pojo;

import com.google.gson.annotations.SerializedName;

public class SubTitlePojo {
    @SerializedName("sub_title")
    String sub_title;
    @SerializedName("sub_title_id")
    String sub_title_id;

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getSub_title_id() {
        return sub_title_id;
    }

    public void setSub_title_id(String sub_title_id) {
        this.sub_title_id = sub_title_id;
    }

    @Override
    public String toString() {
        return "SubTitlePojo{" +
                "sub_title='" + sub_title + '\'' +
                ", sub_title_id='" + sub_title_id + '\'' +
                '}';
    }
}
