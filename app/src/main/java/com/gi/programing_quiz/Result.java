package com.gi.programing_quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Result extends AppCompatActivity {
    TextView result, setTitle, nonAttemptText, attemptText;
    String count, title, totalQuestion, nonAttempt, Attempt;
    Button viewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Result</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);

        result = findViewById(R.id.result);
        nonAttemptText = findViewById(R.id.nonAttemptText);
        attemptText = findViewById(R.id.attemptText);
        setTitle = findViewById(R.id.setTitle);
        viewResult = findViewById(R.id.viewResult);

        count = getIntent().getStringExtra("count");
        title = getIntent().getStringExtra("title");
        totalQuestion = getIntent().getStringExtra("totalQuestion");
        nonAttempt = getIntent().getStringExtra("nonAttempt");
        Attempt = Integer.parseInt(totalQuestion) - Integer.parseInt(nonAttempt) + "";

        result.setText(count + "/" + totalQuestion);
        setTitle.setText(title);
        nonAttemptText.setText(nonAttempt);
        attemptText.setText(Attempt);


        viewResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Result.this, ViewSummary.class);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, Home.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}