package com.gi.giquiz.Registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gi.giquiz.Home;
import com.gi.giquiz.Network.Retro;
import com.gi.giquiz.Network.RetroInterface;
import com.gi.giquiz.Pojo.UserPojo;
import com.gi.giquiz.R;
import com.gi.giquiz.SharedPrefrence.SharedPre;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    TextInputEditText mobile, password;
    TextView forgotPass, signup;
    Button login;
    SharedPre sharedPre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mobile = findViewById(R.id.mobile);
        password = findViewById(R.id.password);
        forgotPass = findViewById(R.id.forgotPass);
        signup = findViewById(R.id.signup);
        login = findViewById(R.id.login);
        sharedPre = new SharedPre(this);


        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ForgotOTP.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogIn();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, OTP.class);
                startActivity(intent);
            }
        });
    }

    public void LogIn() {
        int c = 0;
        if (mobile.getText().toString().isEmpty()) {
            mobile.setError("Enter mobile");
            c++;
        } else if (password.getText().toString().isEmpty()) {
            password.setError("Enter Password");
            c++;
        }
        if (c == 0) {
            if (mobile.getText().toString().length() < 10) {
                Toast.makeText(this, "Please enter valid mobile", Toast.LENGTH_SHORT).show();
            } else {
                Retro.getRetrofit(this).create(RetroInterface.class).login(mobile.getText().toString(), password.getText().toString()).enqueue(new Callback<UserPojo>() {
                    @Override
                    public void onResponse(Call<UserPojo> call, Response<UserPojo> response) {
                        UserPojo pojo = response.body();
                        if (pojo.getStatus().equals("success")) {
                            sharedPre.writeData("userID", pojo.getUser_id());
                            sharedPre.writeData("status", "LoggedIn");
                            Intent intent = new Intent(Login.this, Home.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Login.this, "Wrong Credential", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserPojo> call, Throwable t) {

                    }
                });
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}