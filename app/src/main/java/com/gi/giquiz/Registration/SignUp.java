package com.gi.giquiz.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gi.giquiz.Network.Retro;
import com.gi.giquiz.Network.RetroInterface;
import com.gi.giquiz.R;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {
    TextInputEditText name, email, password, confirmPass;
    Button signup;
    TextView login;
    String userMobile;

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
            if (password.getText().toString().equals(confirmPass.getText().toString())) {
                Retro.getRetrofit(this).create(RetroInterface.class).signup(name.getText().toString(), email.getText().toString(), userMobile, password.getText().toString()).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d("gilog_signup", response.body().toString());
                        Log.d("gilog_signup", response.toString());

                        Toast.makeText(SignUp.this, "Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUp.this, Login.class);
                        startActivity(intent);
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