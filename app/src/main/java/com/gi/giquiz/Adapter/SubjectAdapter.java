package com.gi.giquiz.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gi.giquiz.Pojo.SubjectPojo;
import com.gi.giquiz.R;
import com.gi.giquiz.SubjectTitle;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectHolder> {

    List<SubjectPojo> subjectData;
    Context context;

    public SubjectAdapter(List<SubjectPojo> subjectData, Context context) {
        this.subjectData = subjectData;
        this.context = context;
    }

    @Override
    public SubjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_design, parent, false);
        SubjectHolder holder = new SubjectHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SubjectAdapter.SubjectHolder holder, int position) {
        SubjectPojo pojo = subjectData.get(position);
        Glide.with(context)
                .load(context.getString(R.string.IP_SubImage) + pojo.getSubject_image())
                .into(holder.sub_img);
        holder.sub_name.setText(pojo.getSubject_name());
        holder.subjectId = pojo.getSubject_id();

        holder.clickSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SubjectTitle.class);
                intent.putExtra("subjectId", holder.subjectId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectData.size();
    }

    class SubjectHolder extends RecyclerView.ViewHolder {
        ImageView sub_img;
        CardView clickSubject;
        TextView sub_name;
        String subjectId;

        public SubjectHolder(View itemView) {
            super(itemView);
            sub_img = itemView.findViewById(R.id.sub_img);
            sub_name = itemView.findViewById(R.id.sub_name);
            clickSubject = itemView.findViewById(R.id.clickSubject);
        }
    }
}
