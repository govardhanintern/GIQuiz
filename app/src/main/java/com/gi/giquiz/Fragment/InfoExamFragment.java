package com.gi.giquiz.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.gi.giquiz.R;

public class InfoExamFragment extends Fragment {
    Context context;

    public InfoExamFragment(){}
    public InfoExamFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_fragment, null);
        return view;
    }
}
