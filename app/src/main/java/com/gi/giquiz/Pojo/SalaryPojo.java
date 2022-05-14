package com.gi.giquiz.Pojo;

import com.google.gson.annotations.SerializedName;

public class SalaryPojo {
    @SerializedName("salary_id")
    String salary_id;
    @SerializedName("salary")
    String salary;

    public String getSalary_id() {
        return salary_id;
    }

    public void setSalary_id(String salary_id) {
        this.salary_id = salary_id;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "SalaryPojo{" +
                "salary_id='" + salary_id + '\'' +
                ", salary='" + salary + '\'' +
                '}';
    }
}
