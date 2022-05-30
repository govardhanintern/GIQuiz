package com.gi.programing_quiz.Registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gi.programing_quiz.Network.Retro;
import com.gi.programing_quiz.Network.RetroInterface;
import com.gi.programing_quiz.R;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckMobile extends AppCompatActivity {
    TextInputEditText mobile;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_mobile);

        mobile = findViewById(R.id.mobile);
        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMobile();
            }
        });
    }

    public void setMobile() {
        int c = 0;
        if (mobile.getText().toString().isEmpty()) {
            mobile.setError("Enter mobile number");
            c++;
        }
        if (c == 0) {
            if (mobile.getText().toString().length() < 10) {
                Toast.makeText(CheckMobile.this, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
            } else {
                Retro.getRetrofit(this).create(RetroInterface.class).checkMobile(mobile.getText().toString()).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body().equals("success")) {
                            Intent intent = new Intent(CheckMobile.this, ForgotPassword.class);
                            intent.putExtra("mobile", mobile.getText().toString());
                            startActivity(intent);
                        } else {
                            Toast.makeText(CheckMobile.this, "Mobile number is not exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }

                });
            }
        }

    }
}