package com.gi.giquiz;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
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

import com.gi.giquiz.Network.Retro;
import com.gi.giquiz.Network.RetroInterface;
import com.gi.giquiz.Pojo.ProgramPojo;
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

public class SolutionProgram extends AppCompatActivity {
    TextView proQue, proSolution, setError;
    String programNo, programQuestion, proId;
    ScrollView scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution_program);

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
        setProSolution();
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
                createPdf();
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
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + "gi_quiz_" + programNo + ".pdf";
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
            Toast.makeText(this, "Exported Successfully", Toast.LENGTH_SHORT).show();
        } catch (DocumentException e) {
            e.printStackTrace();
            Toast.makeText(this, "Sorry.", Toast.LENGTH_SHORT).show();
        }
        document.close();

    }


}