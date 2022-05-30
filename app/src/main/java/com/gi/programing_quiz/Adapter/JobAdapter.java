package com.gi.programing_quiz.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gi.programing_quiz.JobDetails;
import com.gi.programing_quiz.Pojo.JobPojo;
import com.gi.programing_quiz.R;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobHolder> {

    List<JobPojo> jobData;
    Context context;

    public JobAdapter(List<JobPojo> jobData, Context context) {
        this.jobData = jobData;
        this.context = context;
    }

    @Override
    public JobHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_design, parent, false);
        JobHolder holder = new JobHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(JobAdapter.JobHolder holder, int position) {
        JobPojo pojo = jobData.get(position);
        holder.job_title.setText(pojo.getJob_title());
        holder.post_date.setText(pojo.getPost_date());
        holder.location.setText(pojo.getCity());
        holder.experience.setText("Experience : " + pojo.getExperience());
        holder.salary.setText("₹" + pojo.getSalary());
        holder.company_name.setText(pojo.getCompany_name());

        holder.jobLogo = pojo.getCompany_logo();
        holder.jobTitle = pojo.getJob_title();
        holder.jobPostedData = pojo.getPost_date();
        holder.jobExperience = pojo.getExperience();
        holder.jobSalary = pojo.getSalary();
        holder.jobDesc = pojo.getDescription();
        holder.jobResp = pojo.getResponsibility();
        holder.jobLocation = pojo.getCity();
        holder.jobPersonName = pojo.getPerson_name();
        holder.jobContact = pojo.getContact();
        holder.jobEmail = pojo.getEmail();
        holder.companyName = pojo.getCompany_name();

        holder.jobCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, JobDetails.class);
                intent.putExtra("jobLogo", holder.jobLogo);
                intent.putExtra("jobTitle", holder.jobTitle);
                intent.putExtra("jobPostedData", holder.jobPostedData);
                intent.putExtra("jobExperience", holder.jobExperience);
                intent.putExtra("jobSalary", holder.jobSalary);
                intent.putExtra("jobDesc", holder.jobDesc);
                intent.putExtra("jobResp", holder.jobResp);
                intent.putExtra("jobLocation", holder.jobLocation);
                intent.putExtra("jobPersonName", holder.jobPersonName);
                intent.putExtra("jobContact", holder.jobContact);
                intent.putExtra("jobEmail", holder.jobEmail);
                intent.putExtra("companyName", holder.companyName);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return jobData.size();
    }

    public class JobHolder extends RecyclerView.ViewHolder {
        String jobLogo, jobTitle, jobPostedData, jobExperience, jobSalary,
                jobDesc, jobResp, jobLocation, jobPersonName, jobContact, jobEmail, companyName;
        TextView job_title, post_date, location, experience, salary, company_name;
        CardView jobCard;

        public JobHolder(View itemView) {
            super(itemView);
            job_title = itemView.findViewById(R.id.job_title);
            post_date = itemView.findViewById(R.id.post_date);
            location = itemView.findViewById(R.id.location);
            experience = itemView.findViewById(R.id.experience);
            salary = itemView.findViewById(R.id.salary);
            jobCard = itemView.findViewById(R.id.jobCard);
            company_name = itemView.findViewById(R.id.company_name);
        }
    }
}
