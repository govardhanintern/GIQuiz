package com.gi.programing_quiz.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gi.programing_quiz.Adapter.SubjectAdapter;
import com.gi.programing_quiz.Registration.Login;
import com.gi.programing_quiz.SharedPrefrence.SharedPre;
import com.gi.programing_quiz.StaticFunction.AlertMessage;
import com.gi.programing_quiz.Network.Retro;
import com.gi.programing_quiz.Network.RetroInterface;
import com.gi.programing_quiz.Pojo.SubjectPojo;
import com.gi.programing_quiz.R;
import com.gi.programing_quiz.StaticFunction.ErrorDialog;
import com.gi.programing_quiz.StaticFunction.ErrorLogs;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    Context context;
    RecyclerView subjectRView;
    List<SubjectPojo> subjectData;
    ProgressDialog dialog;
    TextView textError;
    AlertDialog.Builder builder;
    private AdView adView;
    SharedPre sharedPre;

    public HomeFragment() {

    }

    public HomeFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        View view = inflater.inflate(R.layout.home_fragment, null);
        textError = view.findViewById(R.id.textError);
        subjectRView = view.findViewById(R.id.subjectRView);
        subjectRView.setLayoutManager(new GridLayoutManager(context, 2));
        subjectData = new ArrayList<>();
        sharedPre = new SharedPre(context);

        adView = view.findViewById(R.id.adManagerAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


        getSubjects();
        return view;
    }

    public void getSubjects() {
        dialog = AlertMessage.showProgressDialog(context);
        Retro.getRetrofit(getContext()).create(RetroInterface.class).fetchSubjects().enqueue(new Callback<List<SubjectPojo>>() {
            @Override
            public void onResponse(Call<List<SubjectPojo>> call, Response<List<SubjectPojo>> response) {
                try {
                    if (response.body().isEmpty()) {
                        textError.setVisibility(View.VISIBLE);
                        subjectRView.setVisibility(View.GONE);
                        dialog.dismiss();
                    } else {
                        subjectData = response.body();
                        SubjectAdapter adapter = new SubjectAdapter(subjectData, context);
                        subjectRView.setAdapter(adapter);
                        dialog.dismiss();
                    }
                } catch (Exception e) {
                    ErrorLogs.insertLogs(context, sharedPre.readData("userID", ""), e.toString());
                    dialog.dismiss();
                    builder = ErrorDialog.showBuilder(context);
                    builder.show();
                }
            }

            @Override
            public void onFailure(Call<List<SubjectPojo>> call, Throwable t) {
                ErrorLogs.insertLogs(context, sharedPre.readData("userID", ""), t.toString());
                dialog.dismiss();
                builder = ErrorDialog.showBuilder(context);
                builder.show();
            }
        });
    }
}
