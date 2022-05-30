package com.gi.programing_quiz.Registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gi.programing_quiz.Network.Retro;
import com.gi.programing_quiz.Network.RetroInterface;
import com.gi.programing_quiz.R;
import com.google.android.gms.safetynet.HarmfulAppsData;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotOTP extends AppCompatActivity {
    Button submit;
    TextInputEditText mobile;
    private FirebaseAuth mAuth;
    private String verificationId;
    String verifyotp = "";
    String mob = "";
    TextView SetError;
    String mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_otp);

        submit = findViewById(R.id.submit);
        SetError = findViewById(R.id.SetError);
        mobile = findViewById(R.id.mobile);

        mAuth = FirebaseAuth.getInstance();
        SafetyNet.getClient(this)
                .listHarmfulApps()
                .addOnCompleteListener(new OnCompleteListener<SafetyNetApi.HarmfulAppsResponse>() {
                    @Override
                    public void onComplete(Task<SafetyNetApi.HarmfulAppsResponse> task) {
                        Log.d("MY_APP_TAG", "Received listHarmfulApps() result");
                        if (task.isSuccessful()) {
                            SafetyNetApi.HarmfulAppsResponse result = task.getResult();
                            long scanTimeMs = result.getLastScanTimeMs();
                            List<HarmfulAppsData> appList = result.getHarmfulAppsList();
                            if (appList.isEmpty()) {
                                Log.d("MY_APP_TAG", "There are no known " +
                                        "potentially harmful apps installed.");
                            } else {
                                Log.e("MY_APP_TAG",
                                        "Potentially harmful apps are installed!");
                                for (HarmfulAppsData harmfulApp : appList) {
                                    Log.e("MY_APP_TAG", "Information about a harmful app:");
                                    Log.e("MY_APP_TAG",
                                            "  APK: " + harmfulApp.apkPackageName);
                                    Log.e("MY_APP_TAG",
                                            "  SHA-256: " + harmfulApp.apkSha256);
                                    // Categories are defined in VerifyAppsConstants.
                                    Log.e("MY_APP_TAG",
                                            "  Category: " + harmfulApp.apkCategory);
                                }
                            }
                        } else {
                            Log.d("MY_APP_TAG", "An error occurred. " +
                                    "Call isVerifyAppsEnabled() to ensure " +
                                    "that the user has consented.");
                        }
                    }
                });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("gilog", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        //   Toast.makeText(NewOPTActivity.this, task.getResult(), Toast.LENGTH_SHORT).show();
                        // Get new FCM registration token
                    }
                });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButton();
            }
        });
    }

    public void setButton() {
        mob = mobile.getText().toString();

        int c = 0;
        if (mob.isEmpty()) {
            mobile.setError("Enter Mobile No.");
            c++;
        } else if (mob.length() < 10) {
            mobile.setError("Enter Valid No.");
            c++;
        }

        if (c == 0) {
            if (TextUtils.isEmpty(mob)) {
                mobile.setError("Enter Valid No.");
            } else {
                Retro.getRetrofit(this).create(RetroInterface.class).checkMobile(mobile.getText().toString()).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d("gilog_log", response.toString());
                        if (!response.body().equals("success")) {
                            SetError.setText("Mobile Number Is Already Registered");
                        } else {
                            mobileNumber = mobile.getText().toString();
                            sendVerificationCode(mobileNumber);
                            SetError.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("gilog_log", t.toString());
                    }
                });
            }
        }
    }

    private void sendVerificationCode(String number) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91" + number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationId = s;
            BottomSheetDialog dialog = new BottomSheetDialog(ForgotOTP.this);

            dialog.setCancelable(false);
            dialog.setContentView(R.layout.bottomdialogotp);
            final EditText otp = dialog.findViewById(R.id.otp);
            Button verify = dialog.findViewById(R.id.verify);
            verify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int c = 0;

                    if (otp.getText().toString().isEmpty()) {
                        otp.setError("Enter OTP");
                        c++;
                    } else if (otp.length() < 6) {
                        otp.setError("Enter Valid OTP");
                        c++;
                    }

                    if (c == 0) {
                        if (TextUtils.isEmpty(otp.getText().toString())) {
                            otp.setError("Enter Valid OTP");
                        } else {
                            // if OTP field is not empty calling
                            // method to verify the OTP.
                            verifyotp = otp.getText().toString();
                            verifyCode(verifyotp);
                            dialog.dismiss();
                        }
                    }
                }
            });
            dialog.show();
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            final String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(ForgotOTP.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //checkUser();
                            //  Toast.makeText(NewOPTActivity.this, "Code Verified", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ForgotOTP.this, ForgotPassword.class);
                            intent.putExtra("userMobile", mobileNumber);
                            startActivity(intent);
                        } else {

                            Toast.makeText(ForgotOTP.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}