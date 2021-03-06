package com.gi.programing_quiz.Registration;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gi.programing_quiz.StaticFunction.AlertMessage;
import com.gi.programing_quiz.Network.Retro;
import com.gi.programing_quiz.Network.RetroInterface;
import com.gi.programing_quiz.R;
import com.gi.programing_quiz.StaticFunction.ErrorDialog;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity {
    TextInputEditText newPass, confirmPass;
    Button submit;
    String mobile;
    ProgressDialog dialog;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        newPass = findViewById(R.id.newPass);
        confirmPass = findViewById(R.id.confirmPass);
        submit = findViewById(R.id.submit);
        mobile = getIntent().getStringExtra("userMobile");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPass();
            }
        });
    }

    public void forgotPass() {
        int c = 0;
        if (newPass.getText().toString().isEmpty()) {
            newPass.setError("Enter New Password");
            c++;
        } else if (confirmPass.getText().toString().isEmpty()) {
            newPass.setError("Enter Confirm Password");
            c++;
        }
        if (c == 0) {
            dialog = AlertMessage.showProgressDialog(ForgotPassword.this);
            if (newPass.getText().toString().equals(confirmPass.getText().toString())) {
                Retro.getRetrofit(this).create(RetroInterface.class).forgotPassword(mobile, newPass.getText().toString()).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {
                            if (response.body().equals("success")) {
                                Intent intent = new Intent(ForgotPassword.this, Login.class);
                                startActivity(intent);
                                dialog.dismiss();
                            } else {
                                Toast.makeText(ForgotPassword.this, "Contact to admin", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        } catch (Exception e) {
                            builder = ErrorDialog.showBuilder(ForgotPassword.this);
                            builder.show();
                            dialog.dismiss();
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        builder = ErrorDialog.showBuilder(ForgotPassword.this);
                        builder.show();
                        dialog.dismiss();
                    }
                });
            } else {
                Toast.makeText(this, "Password Don't Match", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }
    }

}