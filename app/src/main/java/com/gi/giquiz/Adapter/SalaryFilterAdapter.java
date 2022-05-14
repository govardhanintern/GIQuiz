package com.gi.giquiz.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.gi.giquiz.AppStaticClass.Filter;
import com.gi.giquiz.Pojo.SalaryPojo;
import com.gi.giquiz.R;

import java.util.List;

public class SalaryFilterAdapter extends RecyclerView.Adapter<SalaryFilterAdapter.SalaryHolder> {

    List<SalaryPojo> salaryData;
    Context context;
    Button temp = null;
    boolean flag = true;

    public SalaryFilterAdapter(List<SalaryPojo> salaryData, Context context) {
        this.salaryData = salaryData;
        this.context = context;
    }

    @Override
    public SalaryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_button_design, parent, false);
        SalaryHolder holder = new SalaryHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(SalaryFilterAdapter.SalaryHolder holder, int position) {
        SalaryPojo pojo = salaryData.get(position);
        holder.selectFilter.setText("₹" + pojo.getSalary());

        if (flag) {
            Filter.sal = pojo.getSalary_id();
            temp = holder.selectFilter;
            holder.selectFilter.setBackgroundColor(Color.parseColor("#dddaf4"));
            flag = false;
        }

        holder.selectFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Filter.sal = pojo.getSalary_id();
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
        return salaryData.size();
    }

    public class SalaryHolder extends RecyclerView.ViewHolder {
        Button selectFilter;

        public SalaryHolder(View itemView) {
            super(itemView);
            selectFilter = itemView.findViewById(R.id.selectFilter);
        }
    }
}
