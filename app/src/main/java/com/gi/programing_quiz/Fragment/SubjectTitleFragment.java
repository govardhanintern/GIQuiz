package com.gi.programing_quiz.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gi.programing_quiz.Adapter.SubTitleAdapter;
import com.gi.programing_quiz.Network.Retro;
import com.gi.programing_quiz.Network.RetroInterface;
import com.gi.programing_quiz.Pojo.SubTitlePojo;
import com.gi.programing_quiz.R;
import com.gi.programing_quiz.Registration.Login;
import com.gi.programing_quiz.SharedPrefrence.SharedPre;
import com.gi.programing_quiz.StaticFunction.ErrorDialog;
import com.gi.programing_quiz.StaticFunction.ErrorLogs;

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
    TextView textError;
    AlertDialog.Builder builder;
    SharedPre sharedPre;

    public SubjectTitleFragment() {

    }

    public SubjectTitleFragment(Context context, String subjectId) {
        this.context = context;
        this.subjectId = subjectId;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        View view = inflater.inflate(R.layout.subject_title_fragment, null);
        titleRView = view.findViewById(R.id.titleRView);
        textError = view.findViewById(R.id.textError);
        titleRView.setLayoutManager(new LinearLayoutManager(context));
        subTitleData = new ArrayList<>();
        sharedPre = new SharedPre(context);
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
                try {
                    if (response.body().isEmpty()) {
                        textError.setVisibility(View.VISIBLE);
                        titleRView.setVisibility(View.GONE);
                        dialog.dismiss();
                    } else {
                        subTitleData = response.body();
                        SubTitleAdapter adapter = new SubTitleAdapter(subTitleData, context, status);
                        titleRView.setAdapter(adapter);
                        dialog.dismiss();
                    }
                } catch (Exception e) {
                    ErrorLogs.insertLogs(context, sharedPre.readData("userID", ""), e.toString());
                    builder = ErrorDialog.showBuilder(context);
                    builder.show();
                    dialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<List<SubTitlePojo>> call, Throwable t) {
                ErrorLogs.insertLogs(context, sharedPre.readData("userID", ""), t.toString());
                builder = ErrorDialog.showBuilder(context);
                builder.show();
                dialog.dismiss();
            }
        });
    }
}
