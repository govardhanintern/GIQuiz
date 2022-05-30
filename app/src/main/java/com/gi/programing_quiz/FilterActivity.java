package com.gi.programing_quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gi.programing_quiz.Adapter.JobAdapter;
import com.gi.programing_quiz.AppStaticClass.Filter;
import com.gi.programing_quiz.Network.Retro;
import com.gi.programing_quiz.Network.RetroInterface;
import com.gi.programing_quiz.Pojo.JobPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterActivity extends AppCompatActivity {

    RecyclerView filterRView;
    List<JobPojo> jobData;
    TextView errorText;
    ProgressDialog dialog;
    String cityID, skillID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setTitle(Html.fromHtml("<font color = '#ffffff'>Filter Jobs</font>"));

        dialog = new ProgressDialog(this);
        errorText = findViewById(R.id.errorText);
        filterRView = findViewById(R.id.filterRView);
        filterRView.setLayoutManager(new LinearLayoutManager(this));
        jobData = new ArrayList<>();
        cityID = getIntent().getStringExtra("cityID");
        skillID = getIntent().getStringExtra("skillID");
        setFilterData();
    }

    public void setFilterData() {
        dialog.setMessage("Please Wait");
        dialog.show();
        Retro.getRetrofit(this).create(RetroInterface.class).fetchFilterJob(skillID, Filter.exp, Filter.sal, cityID).enqueue(new Callback<List<JobPojo>>() {
            @Override
            public void onResponse(Call<List<JobPojo>> call, Response<List<JobPojo>> response) {
                if (response.body().isEmpty()) {
                    errorText.setVisibility(View.VISIBLE);
                    filterRView.setVisibility(View.GONE);
                    dialog.dismiss();
                } else {
                    jobData = response.body();
                    JobAdapter adapter = new JobAdapter(jobData, FilterActivity.this);
                    filterRView.setAdapter(adapter);
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<JobPojo>> call, Throwable t) {

            }
        });
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
}