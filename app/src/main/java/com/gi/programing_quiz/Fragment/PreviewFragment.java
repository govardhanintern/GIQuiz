package com.gi.programing_quiz.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.gi.programing_quiz.R;

public class PreviewFragment extends Fragment {
    Context context;

    public PreviewFragment(){}
    public PreviewFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.preview_fragment, null);
        return view;
    }
}
