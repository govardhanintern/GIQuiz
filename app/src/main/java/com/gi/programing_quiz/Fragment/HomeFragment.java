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
import com.gi.programing_quiz.AlertMessage;
import com.gi.programing_quiz.Network.Retro;
import com.gi.programing_quiz.Network.RetroInterface;
import com.gi.programing_quiz.Pojo.SubjectPojo;
import com.gi.programing_quiz.R;
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

    public HomeFragment() {

    }

    public HomeFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, null);
        textError = view.findViewById(R.id.textError);
        subjectRView = view.findViewById(R.id.subjectRView);
        subjectRView.setLayoutManager(new GridLayoutManager(context, 2));
        subjectData = new ArrayList<>();
        builder = new AlertDialog.Builder(context);

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
                    builder.setMessage("Something wants to wrong with server please try again letter");
                    builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            dialog.dismiss();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<SubjectPojo>> call, Throwable t) {
                Log.d("gilog", t.toString());
            }
        });
    }
}
