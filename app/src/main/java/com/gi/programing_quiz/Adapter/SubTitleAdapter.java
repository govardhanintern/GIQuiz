package com.gi.programing_quiz.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gi.programing_quiz.Pojo.SubTitlePojo;
import com.gi.programing_quiz.ProgramList;
import com.gi.programing_quiz.QuestionsOptions;
import com.gi.programing_quiz.R;
import com.gi.programing_quiz.Registration.Login;
import com.gi.programing_quiz.SharedPrefrence.SharedPre;
import com.gi.programing_quiz.VideoPlayer;

import java.util.List;

public class SubTitleAdapter extends RecyclerView.Adapter<SubTitleAdapter.SubTitleHolder> {

    List<SubTitlePojo> subTitleData;
    String status;
    Context context;
    SharedPre sharedPre;
    AlertDialog.Builder builder;

    public SubTitleAdapter(List<SubTitlePojo> subTitleData, Context context, String status) {
        this.subTitleData = subTitleData;
        this.context = context;
        this.status = status;
    }

    @Override
    public SubTitleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.title_design, parent, false);
        SubTitleHolder holder = new SubTitleHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SubTitleAdapter.SubTitleHolder holder, int position) {
        SubTitlePojo pojo = subTitleData.get(position);
        sharedPre = new SharedPre(context);
        builder = new AlertDialog.Builder(context);

        holder.sub_title.setText(pojo.getSub_title());

        holder.titleId = pojo.getSub_title_id();
        holder.title = pojo.getSub_title();

        if (status.equals("MCQ")) {
            holder.titleCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("gilog", "MCQ");
                    if (sharedPre.readData("status", "LoggedOut").equals("LoggedIn")) {
                        Intent intent = new Intent(context, QuestionsOptions.class);
                        intent.putExtra("titleId", holder.titleId);
                        intent.putExtra("title", holder.title);
                        context.startActivity(intent);
                    } else {
                        builder.setMessage("Please Login First");
                        builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(context, Login.class);
                                context.startActivity(intent);
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }

                }
            });
        } else if (status.equals("Program")) {
            holder.titleCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("gilog", "Program");
                    if (sharedPre.readData("status", "LoggedOut").equals("LoggedIn")) {
                        Intent intent = new Intent(context, ProgramList.class);
                        intent.putExtra("titleId", holder.titleId);
                        intent.putExtra("title", holder.title);
                        context.startActivity(intent);
                    } else {
                        builder.setMessage("Please Login First");
                        builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(context, Login.class);
                                context.startActivity(intent);
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                }
            });
        } else {
            holder.titleCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("gilog", "Video");
                    if (sharedPre.readData("status", "LoggedOut").equals("LoggedIn")) {
                        Intent intent = new Intent(context, VideoPlayer.class);
                        intent.putExtra("titleId", holder.titleId);
                        context.startActivity(intent);
                    } else {
                        builder.setMessage("Please Login First");
                        builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(context, Login.class);
                                context.startActivity(intent);
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                }
            });
        }

    }



    @Override
    public int getItemCount() {
        return subTitleData.size();
    }

    class SubTitleHolder extends RecyclerView.ViewHolder {
        TextView sub_title;
        CardView titleCard;
        String titleId, title;

        public SubTitleHolder(View itemView) {
            super(itemView);
            sub_title = itemView.findViewById(R.id.sub_title);
            titleCard = itemView.findViewById(R.id.titleCard);
        }
    }
}
