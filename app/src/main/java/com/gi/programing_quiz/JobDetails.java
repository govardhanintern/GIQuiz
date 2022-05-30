package com.gi.programing_quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class JobDetails extends AppCompatActivity {

    ImageView logo;
    TextView job_title, post_date, location, experience, salary, contact, email, desc, resp, personName, company_name;
    String jobLogo, jobTitle, jobPostedData, jobExperience, jobSalary,
            jobDesc, jobResp, jobLocation, jobPersonName, jobContact, jobEmail, companyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        getSupportActionBar().setTitle(Html.fromHtml("<font color = '#ffffff'>Job Details</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);

        logo = findViewById(R.id.logo);
        job_title = findViewById(R.id.job_title);
        post_date = findViewById(R.id.post_date);
        location = findViewById(R.id.location);
        experience = findViewById(R.id.experience);
        salary = findViewById(R.id.salary);
        contact = findViewById(R.id.contact);
        email = findViewById(R.id.email);
        desc = findViewById(R.id.desc);
        resp = findViewById(R.id.resp);
        personName = findViewById(R.id.personName);
        company_name = findViewById(R.id.company_name);

        jobLogo = getIntent().getStringExtra("jobLogo");
        jobTitle = getIntent().getStringExtra("jobTitle");
        jobPostedData = getIntent().getStringExtra("jobPostedData");
        jobExperience = getIntent().getStringExtra("jobExperience");
        jobSalary = getIntent().getStringExtra("jobSalary");
        jobDesc = getIntent().getStringExtra("jobDesc");
        jobResp = getIntent().getStringExtra("jobResp");
        jobLocation = getIntent().getStringExtra("jobLocation");
        jobPersonName = getIntent().getStringExtra("jobPersonName");
        jobContact = getIntent().getStringExtra("jobContact");
        jobEmail = getIntent().getStringExtra("jobEmail");
        companyName = getIntent().getStringExtra("companyName");

        setJobDetails();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setJobDetails() {
        Glide.with(this)
                .load(this.getString(R.string.IP_JobImage) + jobLogo)
                .into(logo);
        job_title.setText(jobTitle);
        post_date.setText("Posted Date : " + jobPostedData);
        location.setText("Location : " + jobLocation);
        experience.setText("Experience : " + jobExperience);
        salary.setText("Salary : " + "₹" + jobSalary);
        contact.setText(jobContact);
        email.setText(jobEmail);
        desc.setText(jobDesc);
        resp.setText(jobResp);
        personName.setText(jobPersonName);
        company_name.setText(companyName);
    }
}