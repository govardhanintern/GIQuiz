package com.gi.programing_quiz.Registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gi.programing_quiz.AlertMessage;
import com.gi.programing_quiz.Network.Retro;
import com.gi.programing_quiz.Network.RetroInterface;
import com.gi.programing_quiz.R;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {
    TextInputEditText name, email, password, confirmPass;
    Button signup;
    TextView login;
    String userMobile;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.name);
        //mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPass = findViewById(R.id.confirmPass);
        signup = findViewById(R.id.signup);
        login = findViewById(R.id.login);
        userMobile = getIntent().getStringExtra("userMobile");

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });

    }

    public void signUp() {
        int c = 0;
        if (name.getText().toString().isEmpty()) {
            name.setError("Enter Name");
            c++;
        } else if (email.getText().toString().isEmpty()) {
            email.setError("Enter Email");
            c++;
        } else if (password.getText().toString().isEmpty()) {
            password.setError("Enter password");
            c++;
        }
        if (c == 0) {
            dialog = AlertMessage.showProgressDialog(SignUp.this);
            if (password.getText().toString().equals(confirmPass.getText().toString())) {
                Retro.getRetrofit(this).create(RetroInterface.class).signup(name.getText().toString(), email.getText().toString(), userMobile, password.getText().toString()).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body().equals("success")) {
                            Intent intent = new Intent(SignUp.this, Login.class);
                            startActivity(intent);
                            dialog.dismiss();
                        } else {
                            Toast.makeText(SignUp.this, "Contact to admin", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("gilog_signup", t.toString());

                    }
                });
            } else {
                Toast.makeText(this, "Password don't match", Toast.LENGTH_SHORT).show();
            }

        }

    }
}