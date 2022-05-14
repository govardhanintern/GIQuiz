package com.gi.giquiz.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gi.giquiz.Adapter.SubTitleAdapter;
import com.gi.giquiz.Network.Retro;
import com.gi.giquiz.Network.RetroInterface;
import com.gi.giquiz.Pojo.SubTitlePojo;
import com.gi.giquiz.R;
import com.gi.giquiz.SubjectTitle;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubjectTitleFragment extends Fragment {
    Context context;
    RecyclerView titleRView;
    List<SubTitlePojo> subTitleData;
    String subjectId;
    String status = "MCQ";
    ProgressDialog dialog;

    public SubjectTitleFragment() {

    }

    public SubjectTitleFragment(Context context, String subjectId) {
        this.context = context;
        this.subjectId = subjectId;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.subject_title_fragment, null);
        titleRView = view.findViewById(R.id.titleRView);
        titleRView.setLayoutManager(new LinearLayoutManager(context));
        subTitleData = new ArrayList<>();
        dialog = new ProgressDialog(context);
        setSubTitleData();
        return view;
    }

    public void setSubTitleData() {
        dialog.setMessage("Please Wait");
        dialog.show();
        Retro.getRetrofit(getContext()).create(RetroInterface.class).fetchSubTitle(subjectId).enqueue(new Callback<List<SubTitlePojo>>() {
            @Override
            public void onResponse(Call<List<SubTitlePojo>> call, Response<List<SubTitlePojo>> response) {
                subTitleData = response.body();
                SubTitleAdapter adapter = new SubTitleAdapter(subTitleData, context, status);
                titleRView.setAdapter(adapter);
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<SubTitlePojo>> call, Throwable t) {

            }
        });
    }
}
