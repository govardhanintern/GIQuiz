package com.gi.programing_quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.gi.programing_quiz.Adapter.ProQueAdapter;
import com.gi.programing_quiz.Network.Retro;
import com.gi.programing_quiz.Network.RetroInterface;
import com.gi.programing_quiz.Pojo.ProgramPojo;
import com.gi.programing_quiz.Registration.Login;
import com.gi.programing_quiz.SharedPrefrence.SharedPre;
import com.gi.programing_quiz.StaticFunction.ErrorDialog;
import com.gi.programing_quiz.StaticFunction.ErrorLogs;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

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
    private InterstitialAd mInterstitialAd;
    AlertDialog.Builder builder;
    SharedPre sharedPre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_list);


        getSupportActionBar().setTitle(Html.fromHtml("<font color = '#ffffff'>GI Quiz</font>"));

        noData = findViewById(R.id.noData);
        programRView = findViewById(R.id.programRView);
        programRView.setLayoutManager(new LinearLayoutManager(this));
        programData = new ArrayList<>();
        sharedPre = new SharedPre(this);
        titleId = getIntent().getStringExtra("titleId");
        dialog = new ProgressDialog(this);
        setProgramQues();
        loadInterstitialAd();
    }

    public void setProgramQues() {
        dialog.setMessage("Please Wait");
        dialog.show();
        Retro.getRetrofit(this).create(RetroInterface.class).fetchProgramQue(titleId).enqueue(new Callback<List<ProgramPojo>>() {
            @Override
            public void onResponse(Call<List<ProgramPojo>> call, Response<List<ProgramPojo>> response) {
                try {
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
                } catch (Exception e) {
                    ErrorLogs.insertLogs(ProgramList.this, sharedPre.readData("userID", ""), e.toString());
                    dialog.dismiss();
                    builder = ErrorDialog.showBuilder(ProgramList.this);
                    builder.show();
                }
            }

            @Override
            public void onFailure(Call<List<ProgramPojo>> call, Throwable t) {
                ErrorLogs.insertLogs(ProgramList.this, sharedPre.readData("userID", ""), t.toString());
                dialog.dismiss();
                builder = ErrorDialog.showBuilder(ProgramList.this);
                builder.show();
                Log.e("check", t.toString());
            }
        });
    }

    private void loadInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, getString(R.string.interstitial_full_screen), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.d("yash-Ad", "Ad Failed");
                mInterstitialAd = null;
            }

            @Override
            public void onAdLoaded(InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                Log.d("yash-Ad", "Ad Loaded Successfully");
                mInterstitialAd = interstitialAd;
                mInterstitialAd.show(ProgramList.this);
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                        Log.d("yash-Ad", "Ad Dismiss");
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                        Log.d("yash-Ad", "Ad failed");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent();
                        Log.d("yash-Ad", "Ad Loaded Successfully");
                        mInterstitialAd = null;
                    }
                });
            }
        });
    }
}