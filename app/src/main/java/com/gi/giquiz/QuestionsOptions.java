package com.gi.giquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gi.giquiz.AppStaticClass.AppSetting;
import com.gi.giquiz.Network.Retro;
import com.gi.giquiz.Network.RetroInterface;
import com.gi.giquiz.Pojo.QuestionPojo;
import com.gi.giquiz.db.QuestionDB;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionsOptions extends AppCompatActivity {
    TextView question, heading, qCount, op_c, op_d;
    RadioButton a, b, c, d;
    Button next, previous, submit;
    private AdView adView;
    int count = 0;
    String titleId, title, totalQuestion;
    String answerValue = "";
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_options);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>GI Quiz</font>"));

        question = findViewById(R.id.question);
        a = findViewById(R.id.a);
        b = findViewById(R.id.b);
        c = findViewById(R.id.c);
        d = findViewById(R.id.d);
        heading = findViewById(R.id.heading);
        qCount = findViewById(R.id.qCount);
        op_c = findViewById(R.id.c_op);
        op_d = findViewById(R.id.d_op);
        next = findViewById(R.id.next);
        submit = findViewById(R.id.submit);
        previous = findViewById(R.id.previous);
        // questionData = new ArrayList<>();
        titleId = getIntent().getStringExtra("titleId");
        title = getIntent().getStringExtra("title");
        heading.setText(title);
        dialog = new ProgressDialog(this);
        adView = findViewById(R.id.adManagerAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRadioValue(true, false, false, false, "A");

            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRadioValue(false, true, false, false, "B");
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRadioValue(false, false, true, false, "C");
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRadioValue(false, false, false, true, "D");
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                setRadioValue(false, false, false, false, "");
                setValues(count, QuestionDB.questionData, QuestionDB.questionData.size());
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                setValues(count, QuestionDB.questionData, QuestionDB.questionData.size());
                switch (QuestionDB.questionData.get(count).getUser_answer()) {
                    case "A":
                        setRadioValue(true, false, false, false, "A");
                        break;
                    case "B":
                        setRadioValue(false, true, false, false, "B");
                        break;
                    case "C":
                        setRadioValue(false, false, true, false, "C");
                        break;
                    case "D":
                        setRadioValue(false, false, false, true, "D");
                        break;
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValues(count, QuestionDB.questionData, QuestionDB.questionData.size());
                if (count == QuestionDB.questionData.size()) {
                } else {
                    checkAnswer();
                }
            }
        });
        setQuestionData();
    }

    void setRadioValue(boolean value1, boolean value2, boolean value3, boolean value4, String opValue) {
        try {
            a.setChecked(value1);
            b.setChecked(value2);
            c.setChecked(value3);
            d.setChecked(value4);
            answerValue = opValue;
            if (opValue != "") {
                QuestionDB.questionData.get(count).setUser_answer(opValue);
            }

        } catch (Exception e) {

        }
    }

    public void setQuestionData() {
        dialog.setMessage("Please Wait");
        dialog.show();
        Retro.getRetrofit(this).create(RetroInterface.class).fetchQuestionOptions(titleId).enqueue(new Callback<List<QuestionPojo>>() {
            @Override
            public void onResponse(Call<List<QuestionPojo>> call, Response<List<QuestionPojo>> response) {
                QuestionDB.questionData = response.body();
                setValues(count, QuestionDB.questionData, QuestionDB.questionData.size());
                totalQuestion = QuestionDB.questionData.size() + "";
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<QuestionPojo>> call, Throwable t) {

            }
        });
    }

    private void setValues(int count, List<QuestionPojo> pojo, int size) {
        op_c.setVisibility(View.VISIBLE);
        op_d.setVisibility(View.VISIBLE);
        c.setVisibility(View.VISIBLE);
        d.setVisibility(View.VISIBLE);
        int QNo = count + 1;
        qCount.setText(QNo + "/" + size);
        question.setText("Q" + QNo + ") " + pojo.get(count).getQ_question());
        a.setText(pojo.get(count).getOptionA());
        b.setText(pojo.get(count).getOptionB());

//        c.setText(pojo.get(count).getOptionC());
//        d.setText(pojo.get(count).getOptionD());
        if (pojo.get(count).getOptionC() == null) {
            c.setVisibility(View.INVISIBLE);
            op_c.setVisibility(View.INVISIBLE);
        } else {
            c.setText(pojo.get(count).getOptionC());
        }
        if (pojo.get(count).getOptionD() == null) {
            op_d.setVisibility(View.INVISIBLE);
            d.setVisibility(View.INVISIBLE);
        } else {
            d.setText(pojo.get(count).getOptionD());
        }

        if (count == 0) {
            previous.setVisibility(View.INVISIBLE);
        } else {
            previous.setVisibility(View.VISIBLE);
        }
        if (count == QuestionDB.questionData.size() - 1) {
            submit.setVisibility(View.VISIBLE);
            next.setVisibility(View.GONE);
        } else {
            submit.setVisibility(View.GONE);
            next.setVisibility(View.VISIBLE);
        }
    }

    public void checkAnswer() {
        int cnt = 0;
        int nonCnt = 0;

        for (int i = 0; i < QuestionDB.questionData.size(); i++) {
            if (QuestionDB.questionData.get(i).getUser_answer().equals(QuestionDB.questionData.get(i).getCorrect_answer())) {
                cnt++;
            } else if (QuestionDB.questionData.get(i).getUser_answer().equals("Z")) {
                nonCnt++;
            }
            Intent intent = new Intent(this, Result.class);
            intent.putExtra("count", String.valueOf(cnt));
            intent.putExtra("title", title);
            intent.putExtra("totalQuestion", totalQuestion);
            intent.putExtra("nonAttempt", String.valueOf(nonCnt));
            startActivity(intent);
        }

    }

}