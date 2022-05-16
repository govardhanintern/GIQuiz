package com.gi.giquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.gi.giquiz.Adapter.ProQueAdapter;
import com.gi.giquiz.Network.Retro;
import com.gi.giquiz.Network.RetroInterface;
import com.gi.giquiz.Pojo.ProgramPojo;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_list);

        getSupportActionBar().setTitle(Html.fromHtml("<font color = '#ffffff'>GI Quiz</font>"));

        noData = findViewById(R.id.noData);
        programRView = findViewById(R.id.programRView);
        programRView.setLayoutManager(new LinearLayoutManager(this));
        programData = new ArrayList<>();
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