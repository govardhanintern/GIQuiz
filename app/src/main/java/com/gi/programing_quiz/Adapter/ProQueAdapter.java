package com.gi.programing_quiz.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gi.programing_quiz.Pojo.ProgramPojo;
import com.gi.programing_quiz.R;
import com.gi.programing_quiz.SolutionProgram;

import java.util.List;

public class ProQueAdapter extends RecyclerView.Adapter<ProQueAdapter.programHolder> {
    List<ProgramPojo> programData;
    String title;
    Context context;

    public ProQueAdapter(List<ProgramPojo> programData, Context context, String title) {
        this.programData = programData;
        this.context = context;
        this.title = title;
    }

    @Override
    public programHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pro_que_design, parent, false);
        programHolder holder = new programHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ProQueAdapter.programHolder holder, int position) {
        ProgramPojo pojo = programData.get(position);
        holder.pq_title.setText(position + 1 + "." + pojo.getP_question());

        holder.proId = pojo.getpList_id();
        holder.programNo = position + 1 + "";
        holder.programQuestion = pojo.getP_question();

        holder.ProTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SolutionProgram.class);
                intent.putExtra("proId", holder.proId);
                intent.putExtra("programNo", holder.programNo);
                intent.putExtra("programQuestion", holder.programQuestion);
                intent.putExtra("title", title);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return programData.size();
    }

    public class programHolder extends RecyclerView.ViewHolder {
        String programNo, programQuestion, proId;
        TextView pq_title;
        CardView ProTitle;

        public programHolder(View itemView) {
            super(itemView);
            pq_title = itemView.findViewById(R.id.pq_title);
            ProTitle = itemView.findViewById(R.id.ProTitle);
        }
    }
}
