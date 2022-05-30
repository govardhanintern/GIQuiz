package com.gi.programing_quiz.Pojo;

import com.google.gson.annotations.SerializedName;

public class UserPojo {
    @SerializedName("user_id")
    String user_id;
    @SerializedName("uName")
    String uName;
    @SerializedName("uEmail")
    String uEmail;
    @SerializedName("uMobile")
    String uMobile;
    @SerializedName("status")
    String status;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getuMobile() {
        return uMobile;
    }

    public void setuMobile(String uMobile) {
        this.uMobile = uMobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserPojo{" +
                "user_id='" + user_id + '\'' +
                ", uName='" + uName + '\'' +
                ", uEmail='" + uEmail + '\'' +
                ", uMobile='" + uMobile + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
