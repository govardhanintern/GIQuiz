package com.gi.giquiz.Fragment;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gi.giquiz.Adapter.SubjectAdapter;
import com.gi.giquiz.Network.Retro;
import com.gi.giquiz.Network.RetroInterface;
import com.gi.giquiz.Pojo.SubjectPojo;
import com.gi.giquiz.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    Context context;
    RecyclerView subjectRView;
    List<SubjectPojo> subjectData;
    ProgressDialog progressDialog;
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
        progressDialog = new ProgressDialog(context);
        builder = new AlertDialog.Builder(context);

        adView = view.findViewById(R.id.adManagerAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        getSubjects();
        return view;
    }

    public void getSubjects() {
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        Retro.getRetrofit(getContext()).create(RetroInterface.class).fetchSubjects().enqueue(new Callback<List<SubjectPojo>>() {
            @Override
            public void onResponse(Call<List<SubjectPojo>> call, Response<List<SubjectPojo>> response) {
                try {
                    if (response.body().isEmpty()) {
                        textError.setVisibility(View.VISIBLE);
                        subjectRView.setVisibility(View.GONE);
                        progressDialog.dismiss();
                    } else {
                        subjectData = response.body();
                        SubjectAdapter adapter = new SubjectAdapter(subjectData, context);
                        subjectRView.setAdapter(adapter);
                        progressDialog.dismiss();
                    }
                } catch (Exception e) {
                    builder.setMessage("Something wants to wrong with server please try again letter");
                    builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            progressDialog.dismiss();
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
