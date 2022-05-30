package com.gi.programing_quiz.Pojo;

import com.google.gson.annotations.SerializedName;

public class JobPojo {
    @SerializedName("job_id")
    String job_id;
    @SerializedName("company_name")
    String company_name;
    @SerializedName("job_title")
    String job_title;
    @SerializedName("company_logo")
    String company_logo;
    @SerializedName("person_name")
    String person_name;
    @SerializedName("email")
    String email;
    @SerializedName("contact")
    String contact;
    @SerializedName("primary_skill")
    String primary_skill;
    @SerializedName("experience")
    String experience;
    @SerializedName("salary")
    String salary;
    @SerializedName("description")
    String description;
    @SerializedName("responsibility")
    String responsibility;
    @SerializedName("city")
    String city;
    @SerializedName("post_date")
    String post_date;

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getCompany_logo() {
        return company_logo;
    }

    public void setCompany_logo(String company_logo) {
        this.company_logo = company_logo;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPrimary_skill() {
        return primary_skill;
    }

    public void setPrimary_skill(String primary_skill) {
        this.primary_skill = primary_skill;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    @Override
    public String toString() {
        return "JobPojo{" +
                "job_id='" + job_id + '\'' +
                ", company_name='" + company_name + '\'' +
                ", job_title='" + job_title + '\'' +
                ", company_logo='" + company_logo + '\'' +
                ", person_name='" + person_name + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                ", primary_skill='" + primary_skill + '\'' +
                ", experience='" + experience + '\'' +
                ", salary='" + salary + '\'' +
                ", description='" + description + '\'' +
                ", responsibility='" + responsibility + '\'' +
                ", city='" + city + '\'' +
                ", post_date='" + post_date + '\'' +
                '}';
    }
}
