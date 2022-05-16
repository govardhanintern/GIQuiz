package com.gi.giquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;

import com.gi.giquiz.Adapter.SubTitleAdapter;
import com.gi.giquiz.AppStaticClass.AppSetting;
import com.gi.giquiz.Fragment.EventFragment;
import com.gi.giquiz.Fragment.HomeFragment;
import com.gi.giquiz.Fragment.InfoExamFragment;
import com.gi.giquiz.Fragment.JobFragment;
import com.gi.giquiz.Fragment.PreviewFragment;
import com.gi.giquiz.Fragment.ProfileFragment;
import com.gi.giquiz.Fragment.ProgramFragment;
import com.gi.giquiz.Fragment.SubjectTitleFragment;
import com.gi.giquiz.Fragment.VideosFragment;
import com.gi.giquiz.Network.Retro;
import com.gi.giquiz.Network.RetroInterface;
import com.gi.giquiz.Pojo.SubTitlePojo;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubjectTitle extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    String subjectId;
    BottomNavigationView bottomNavigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_title);

        subjectId = getIntent().getStringExtra("subjectId");

        getSupportActionBar().setTitle(Html.fromHtml("<font color = '#ffffff'>" + AppSetting.mcqTitle + "</font"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);

        bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        fragment = new SubjectTitleFragment(this, subjectId);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
        loadInterstitialAd();
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (item.getItemId()) {
            case R.id.mcq:
                fragment = new SubjectTitleFragment(this, subjectId);
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
                AppSetting.mcqTitle = "MCQ";
                break;
            case R.id.program:
                fragment = new ProgramFragment(this, subjectId);
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
                AppSetting.mcqTitle = "Program";
                break;
            case R.id.videos:
                fragment = new VideosFragment(this, subjectId);
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
                AppSetting.mcqTitle = "Videos";
                break;
            case R.id.preview:
                fragment = new PreviewFragment(this);
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
                AppSetting.mcqTitle = "Preview";
                break;
            case R.id.inExam:
                fragment = new InfoExamFragment(this);
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
                AppSetting.mcqTitle = "Exam Info";
                break;
        }
        getSupportActionBar().setTitle(Html.fromHtml("<font color = '#ffffff'>" + AppSetting.mcqTitle + "</font"));

        return true;
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
                mInterstitialAd.show(SubjectTitle.this);
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