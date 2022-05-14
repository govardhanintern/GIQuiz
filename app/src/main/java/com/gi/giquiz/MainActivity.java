package com.gi.giquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.gi.giquiz.Registration.Login;

public class MainActivity extends AppCompatActivity implements Runnable {
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();
        handler.postDelayed(this, 3000);
        
    }

    @Override
    public void run() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}