package com.gi.giquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gi.giquiz.Adapter.ProQueAdapter;
import com.gi.giquiz.Network.Retro;
import com.gi.giquiz.Network.RetroInterface;
import com.gi.giquiz.Pojo.ProgramPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProgramList extends AppCompatActivity {

    RecyclerView programRView;
    String titleId;
    List<ProgramPojo> programData;
    TextView noData;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_list);

        noData = findViewById(R.id.noData);
        programRView = findViewById(R.id.programRView);
        programRView.setLayoutManager(new LinearLayoutManager(this));
        programData = new ArrayList<>();
        titleId = getIntent().getStringExtra("titleId");
        dialog = new ProgressDialog(this);
        setProgramQues();
    }

    public void setProgramQues() {
        dialog.setMessage("Please Wait");
        dialog.show();
        Retro.getRetrofit(this).create(RetroInterface.class).fetchProgramQue(titleId).enqueue(new Callback<List<ProgramPojo>>() {
            @Override
            public void onResponse(Call<List<ProgramPojo>> call, Response<List<ProgramPojo>> response) {
                if (response.body().isEmpty()) {
                    noData.setVisibility(View.VISIBLE);
                    programRView.setVisibility(View.GONE);
                    dialog.dismiss();
                } else {
                    programData = response.body();
                    ProQueAdapter adapter = new ProQueAdapter(programData, ProgramList.this);
                    programRView.setAdapter(adapter);
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<ProgramPojo>> call, Throwable t) {

            }
        });
    }
}