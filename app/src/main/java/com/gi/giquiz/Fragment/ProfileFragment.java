package com.gi.giquiz.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gi.giquiz.Home;
import com.gi.giquiz.Network.Retro;
import com.gi.giquiz.Network.RetroInterface;
import com.gi.giquiz.Pojo.UserPojo;
import com.gi.giquiz.ProgramList;
import com.gi.giquiz.R;
import com.gi.giquiz.Registration.Login;
import com.gi.giquiz.SharedPrefrence.SharedPre;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    Context context;
    Button update;
    SharedPre sharedPre;
    AlertDialog.Builder builder;
    TextInputEditText name, mobile, email;
    ProgressDialog dialog;

    public ProfileFragment() {
    }

    public ProfileFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, null);
        name = view.findViewById(R.id.name);
        mobile = view.findViewById(R.id.mobile);
        email = view.findViewById(R.id.email);
        update = view.findViewById(R.id.update);
        sharedPre = new SharedPre(context);
        dialog = new ProgressDialog(context);
        builder = new AlertDialog.Builder(context);

        checkStatus();


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });

        return view;
    }


    public void userData() {
        dialog.setMessage("PLease Wait");
        dialog.show();
        Retro.getRetrofit(context).create(RetroInterface.class).getUserDetails(sharedPre.readData("userID", "")).enqueue(new Callback<UserPojo>() {
            @Override
            public void onResponse(Call<UserPojo> call, Response<UserPojo> response) {
                setUserData(response.body());
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<UserPojo> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    public void setUserData(UserPojo pojo) {
        name.setText(pojo.getuName());
        email.setText(pojo.getuEmail());
        mobile.setText(pojo.getuMobile());
    }

    public void updateUser() {
        Retro.getRetrofit(context).create(RetroInterface.class).updateUserData(sharedPre.readData("userID", ""), name.getText().toString(), email.getText().toString(), mobile.getText().toString()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                builder.setMessage("User Details Updated Successfully");
                builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        userData();
                    }
                });
                builder.show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public void checkStatus() {
        if (sharedPre.readData("status", "LoggedOut").equals("LoggedIn")) {
            userData();
        } else {
            builder.setMessage("Please Login First");
            builder.setCancelable(false);
            builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(context, Login.class);
                    context.startActivity(intent);
                    dialog.dismiss();
                }
            });
            builder.show();
        }
    }
}
