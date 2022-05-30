package com.gi.programing_quiz.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.gi.programing_quiz.AppStaticClass.Filter;
import com.gi.programing_quiz.Pojo.ExperiencePojo;
import com.gi.programing_quiz.R;

import java.util.List;

public class ExperienceFilterAdapter extends RecyclerView.Adapter<ExperienceFilterAdapter.ExperienceHolder> {
    List<ExperiencePojo> experienceData;
    Context context;
    Button temp = null;
    boolean flag = true;

    public ExperienceFilterAdapter(List<ExperiencePojo> experienceData, Context context) {
        this.experienceData = experienceData;
        this.context = context;
    }

    @Override
    public ExperienceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_button_design, parent, false);
        ExperienceHolder holder = new ExperienceHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ExperienceFilterAdapter.ExperienceHolder holder, int position) {
        ExperiencePojo pojo = experienceData.get(position);
        holder.selectFilter.setText(pojo.getExperience());

        if (flag) {
            Filter.exp = pojo.getExperience_id();
            temp = holder.selectFilter;
            holder.selectFilter.setBackgroundColor(Color.parseColor("#dddaf4"));
            flag = false;
        }

        holder.selectFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Filter.exp = pojo.getExperience_id();
                if (temp != null) {
                    temp.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
                temp = holder.selectFilter;
                holder.selectFilter.setBackgroundColor(Color.parseColor("#dddaf4"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return experienceData.size();
    }

    public class ExperienceHolder extends RecyclerView.ViewHolder {
        Button selectFilter;

        public ExperienceHolder(View itemView) {
            super(itemView);
            selectFilter = itemView.findViewById(R.id.selectFilter);
        }
    }
}
