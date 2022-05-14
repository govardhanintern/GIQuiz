package com.gi.giquiz.Network;

import com.gi.giquiz.Pojo.CityPojo;
import com.gi.giquiz.Pojo.ExperiencePojo;
import com.gi.giquiz.Pojo.JobPojo;
import com.gi.giquiz.Pojo.LinkPojo;
import com.gi.giquiz.Pojo.ProgramPojo;
import com.gi.giquiz.Pojo.QuestionPojo;
import com.gi.giquiz.Pojo.SalaryPojo;
import com.gi.giquiz.Pojo.SkillPojo;
import com.gi.giquiz.Pojo.SubTitlePojo;
import com.gi.giquiz.Pojo.SubjectPojo;
import com.gi.giquiz.Pojo.UserPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetroInterface {

    @POST("fetchSubjects.php")
    Call<List<SubjectPojo>> fetchSubjects();

    @FormUrlEncoded
    @POST("fetchSubTitle.php")
    Call<List<SubTitlePojo>> fetchSubTitle(@Field("subject_id") String subject_id);

    @FormUrlEncoded
    @POST("fetchQuestionOptions.php")
    Call<List<QuestionPojo>> fetchQuestionOptions(@Field("sub_title_id") String sub_title_id);

    @FormUrlEncoded
    @POST("signup.php")
    Call<String> signup(@Field("uName") String uName,
                        @Field("uEmail") String uEmail,
                        @Field("uMobile") String uMobile,
                        @Field("uPassword") String uPassword);

    @FormUrlEncoded
    @POST("login.php")
    Call<UserPojo> login(@Field("uMobile") String uMobile,
                         @Field("uPassword") String uPassword);

    @FormUrlEncoded
    @POST("fetchProgramQue.php")
    Call<List<ProgramPojo>> fetchProgramQue(@Field("sub_title_id") String sub_title_id);

    @FormUrlEncoded
    @POST("fetchProSolution.php")
    Call<ProgramPojo> fetchProSolution(@Field("pList_id") String pList_id);

    @GET("fetchJobData.php")
    Call<List<JobPojo>> fetchJobData();

    @GET("fetchSalary.php")
    Call<List<SalaryPojo>> fetchSalary();

    @GET("fetchExperience.php")
    Call<List<ExperiencePojo>> fetchExperience();

    @FormUrlEncoded
    @POST("fetchFilterJob.php")
    Call<List<JobPojo>> fetchFilterJob(@Field("primary_skill_id") String primary_skill_id,
                                       @Field("experience_id") String experience_id,
                                       @Field("salary_id") String salary_id,
                                       @Field("city_id") String city_id);

    @GET("fetchCity.php")
    Call<List<CityPojo>> fetchCity();

    @GET("fetchSkill.php")
    Call<List<SkillPojo>> fetchSkill();

    @FormUrlEncoded
    @POST("getUserDetails.php")
    Call<UserPojo> getUserDetails(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("updateUserData.php")
    Call<String> updateUserData(@Field("user_id") String user_id,
                                @Field("uName") String uName,
                                @Field("uEmail") String uEmail,
                                @Field("uMobile") String uMobile);

    @FormUrlEncoded
    @POST("checkMobile.php")
    Call<String> checkMobile(@Field("uMobile") String uMobile);

    @FormUrlEncoded
    @POST("forgotPassword.php")
    Call<String> forgotPassword(@Field("uMobile") String uMobile,
                                @Field("uPassword") String uPassword);

    @Headers("Content-Type: application/json")
    @POST("insertKey.php")
    Call<String> insertKey(@Query("user_fcm") String user_fcm);

    @FormUrlEncoded
    @POST("fetchVideoLink.php")
    Call<LinkPojo> fetchVideoLink(@Field("sub_title_id") String sub_title_id);
}
