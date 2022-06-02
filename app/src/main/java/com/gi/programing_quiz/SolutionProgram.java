package com.gi.programing_quiz;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.gi.programing_quiz.Network.Retro;
import com.gi.programing_quiz.Network.RetroInterface;
import com.gi.programing_quiz.Pojo.ProgramPojo;
import com.gi.programing_quiz.StaticFunction.ErrorDialog;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class SolutionProgram extends AppCompatActivity {
    TextView proQue, proSolution, setError;
    String programNo, programQuestion, proId, title;
    ScrollView scroll;
    private InterstitialAd mInterstitialAd;
    AlertDialog.Builder builder;
    AlertDialog.Builder builder1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution_program);

        builder = new AlertDialog.Builder(this);
        builder1 = new AlertDialog.Builder(this);

        getSupportActionBar().setTitle(Html.fromHtml("<font color = '#ffffff'>Solution</font>"));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        }
        proQue = findViewById(R.id.proQue);
        proSolution = findViewById(R.id.proSolution);
        setError = findViewById(R.id.setError);
        scroll = findViewById(R.id.scroll);

        programNo = getIntent().getStringExtra("programNo");
        programQuestion = getIntent().getStringExtra("programQuestion");
        proId = getIntent().getStringExtra("proId");
        title = getIntent().getStringExtra("title");
        setProSolution();
        // loadInterstitialAd();
    }

    public void setProSolution() {
        Retro.getRetrofit(this).create(RetroInterface.class).fetchProSolution(proId).enqueue(new Callback<ProgramPojo>() {
            @Override
            public void onResponse(Call<ProgramPojo> call, Response<ProgramPojo> response) {
                ProgramPojo pojo = response.body();
                if (pojo.getStatus().equals("success")) {
                    setSolutionData(response.body());
                } else {
                    setError.setVisibility(View.VISIBLE);
                    scroll.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ProgramPojo> call, Throwable t) {

            }
        });
    }

    public void setSolutionData(ProgramPojo pojo) {
        proQue.setText(programNo + ". " + programQuestion);
        proSolution.setText(pojo.getSolution());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String url = "https://play.google.com/store/apps/details?id=com.gi.giquiz";
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, url);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
                return true;
            case R.id.download:
                loadInterstitialAd();
                builder.setTitle("Download");
                builder.setMessage("Are you sure you want to Download this question and answer as a PDF?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        createPdf();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    public void createPdf() {
        String question = proQue.getText().toString();
        String solution = proSolution.getText().toString();
        String path = Environment.getExternalStorageDirectory().toString() + "/Download/" + "Programing_Quiz_" + title + "_" + programNo + ".pdf";
        Log.d("gilog", path);
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file.getAbsoluteFile()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.open();
        try {
            document.add(new Paragraph(question));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph(solution));
            builder1.setMessage("PDF Download Successful..");
            builder1.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder1.show();
            //Toast.makeText(this, "Exported Successfully", Toast.LENGTH_SHORT).show();
        } catch (DocumentException e) {
            e.printStackTrace();
            Toast.makeText(this, "Sorry.", Toast.LENGTH_SHORT).show();
        }
        document.close();
    }

    private void loadInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, getString(R.string.rewarded_video), adRequest, new InterstitialAdLoadCallback() {
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
                mInterstitialAd.show(SolutionProgram.this);
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