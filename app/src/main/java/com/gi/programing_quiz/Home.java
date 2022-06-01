package com.gi.programing_quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.gi.programing_quiz.AppStaticClass.AppSetting;
import com.gi.programing_quiz.Fragment.EventFragment;
import com.gi.programing_quiz.Fragment.HomeFragment;
import com.gi.programing_quiz.Fragment.JobFragment;
import com.gi.programing_quiz.Fragment.ProfileFragment;
import com.gi.programing_quiz.Network.Retro;
import com.gi.programing_quiz.Network.RetroInterface;
import com.gi.programing_quiz.Registration.Login;
import com.gi.programing_quiz.Registration.SignUp;
import com.gi.programing_quiz.SharedPrefrence.SharedPre;
import com.gi.programing_quiz.StaticFunction.ErrorDialog;
import com.gi.programing_quiz.StaticFunction.ErrorLogs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;
    SharedPre sharedPre;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPre = new SharedPre(this);
        builder = new AlertDialog.Builder(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>" + AppSetting.homeTitle + "</font>"));

        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.d("FCMLOG", "fail");
                            Log.w("gilog", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        //  Toast.makeText(context, task.getResult(), Toast.LENGTH_SHORT).show();
                        Retro.getRetrofit(Home.this).create(RetroInterface.class).insertKey(task.getResult()).enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                try {
                                    Log.d("gilog", response.toString());
                                    Log.d("gilog", response.body().toString());
                                } catch (Exception e) {
                                    ErrorLogs.insertLogs(Home.this, sharedPre.readData("userID", ""), e.toString());
                                    builder = ErrorDialog.showBuilder(Home.this);
                                    builder.show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                ErrorLogs.insertLogs(Home.this, sharedPre.readData("userID", ""), t.toString());
                                Log.d("gilog", t.toString());
                                builder = ErrorDialog.showBuilder(Home.this);
                                builder.show();
                            }
                        });

                    }
                });

        fragment = new HomeFragment(this);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (item.getItemId()) {
            case R.id.home:
                AppSetting.homeTitle = "Home";
                fragment = new HomeFragment(this);
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
                break;
            case R.id.event:
                AppSetting.homeTitle = "Event";

                fragment = new EventFragment(this);
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
                break;
            case R.id.job:
                AppSetting.homeTitle = "Jobs";
                fragment = new JobFragment(this);
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
                break;
            case R.id.profile:
                AppSetting.homeTitle = "Profile";
                fragment = new ProfileFragment(this);
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
                break;

        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>" + AppSetting.homeTitle + "</font>"));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finishAffinity();
                return true;
            case R.id.logout:
                logOut();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (sharedPre.readData("status", "LoggedOut").equals("LoggedIn")) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.logout_menu, menu);
        }
        return true;
    }

    public void logOut() {
        builder.setMessage("Are You Sure you want to Logout");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sharedPre.writeData("userID", "");
                sharedPre.writeData("status", "LoggedOut");
                Intent intent = new Intent(Home.this, Login.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}