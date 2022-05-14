package com.gi.giquiz.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gi.giquiz.Adapter.ExperienceFilterAdapter;
import com.gi.giquiz.Adapter.JobAdapter;
import com.gi.giquiz.Adapter.SalaryFilterAdapter;
import com.gi.giquiz.AppStaticClass.Filter;
import com.gi.giquiz.FilterActivity;
import com.gi.giquiz.Network.Retro;
import com.gi.giquiz.Network.RetroInterface;
import com.gi.giquiz.Pojo.CityPojo;
import com.gi.giquiz.Pojo.ExperiencePojo;
import com.gi.giquiz.Pojo.JobPojo;
import com.gi.giquiz.Pojo.SalaryPojo;
import com.gi.giquiz.Pojo.SkillPojo;
import com.gi.giquiz.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobFragment extends Fragment {
    Context context;
    RecyclerView jobRView, salaryRView, experienceRView;
    List<JobPojo> jobData;
    List<SalaryPojo> salaryData;
    List<ExperiencePojo> ExperienceData;
    LinearLayout filter;
    BottomSheetDialog bottomSheetDialog;
    ProgressDialog dialog;
    Button search;
    Spinner skillSpinner, citySpinner;
    ArrayList cityId, cityName, skillId, skillName;

    public JobFragment() {
    }

    public JobFragment(Context context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.job_fragment, null);
        jobRView = view.findViewById(R.id.jobRView);
        filter = view.findViewById(R.id.filter);
        jobRView.setLayoutManager(new LinearLayoutManager(context));
        jobData = new ArrayList<>();
        salaryData = new ArrayList<>();
        ExperienceData = new ArrayList<>();
        dialog = new ProgressDialog(context);
        bottomSheetDialog = new BottomSheetDialog(context);
        setJob();

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheet();
            }
        });

        return view;
    }

    public void setJob() {
        dialog.setMessage("Please Wait");
        dialog.show();
        Retro.getRetrofit(context).create(RetroInterface.class).fetchJobData().enqueue(new Callback<List<JobPojo>>() {
            @Override
            public void onResponse(Call<List<JobPojo>> call, Response<List<JobPojo>> response) {
                jobData = response.body();
                JobAdapter adapter = new JobAdapter(jobData, context);
                jobRView.setAdapter(adapter);
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<JobPojo>> call, Throwable t) {

            }
        });
    }

    public void bottomSheet() {
        bottomSheetDialog.setContentView(R.layout.filter_design);
        search = bottomSheetDialog.findViewById(R.id.search);
        skillSpinner = bottomSheetDialog.findViewById(R.id.skillSpinner);
        citySpinner = bottomSheetDialog.findViewById(R.id.citySpinner);

        getCity();
        getSkill();

        salaryRView = bottomSheetDialog.findViewById(R.id.salaryRView);
        salaryRView.setLayoutManager(new GridLayoutManager(context, 3));
        setSalaryFilter();

        experienceRView = bottomSheetDialog.findViewById(R.id.experienceRView);
        experienceRView.setLayoutManager(new GridLayoutManager(context, 3));
        setExperienceFilter();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FilterActivity.class);
                intent.putExtra("cityID", cityId.get(citySpinner.getSelectedItemPosition()).toString());
                intent.putExtra("skillID", skillId.get(skillSpinner.getSelectedItemPosition()).toString());
                startActivity(intent);
                bottomSheetDialog.dismiss();

            }
        });

        bottomSheetDialog.show();
    }

    public void setSalaryFilter() {
        dialog.setMessage("Please Wait");
        dialog.show();
        Retro.getRetrofit(context).create(RetroInterface.class).fetchSalary().enqueue(new Callback<List<SalaryPojo>>() {
            @Override
            public void onResponse(Call<List<SalaryPojo>> call, Response<List<SalaryPojo>> response) {
                salaryData = response.body();
                SalaryFilterAdapter adapter = new SalaryFilterAdapter(salaryData, context);
                salaryRView.setAdapter(adapter);
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<SalaryPojo>> call, Throwable t) {

            }
        });
    }

    public void setExperienceFilter() {
        dialog.setMessage("Please Wait");
        dialog.show();
        Retro.getRetrofit(context).create(RetroInterface.class).fetchExperience().enqueue(new Callback<List<ExperiencePojo>>() {
            @Override
            public void onResponse(Call<List<ExperiencePojo>> call, Response<List<ExperiencePojo>> response) {
                ExperienceData = response.body();
                ExperienceFilterAdapter adapter = new ExperienceFilterAdapter(ExperienceData, context);
                experienceRView.setAdapter(adapter);
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<ExperiencePojo>> call, Throwable t) {

            }
        });
    }

    public void getCity() {
        Retro.getRetrofit(context).create(RetroInterface.class).fetchCity().enqueue(new Callback<List<CityPojo>>() {
            @Override
            public void onResponse(Call<List<CityPojo>> call, Response<List<CityPojo>> response) {
                setCity(response.body());
            }

            @Override
            public void onFailure(Call<List<CityPojo>> call, Throwable t) {

            }
        });
    }

    public void setCity(List<CityPojo> pojo) {
        cityId = new ArrayList<String>(pojo.size());
        cityName = new ArrayList<String>(pojo.size());

        for (CityPojo temp : pojo) {
            cityId.add(temp.getCity_id());
            cityName.add(temp.getCity_name());
        }
        ArrayAdapter setCityData = new ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, cityName) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                }
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        citySpinner.setAdapter(setCityData);
    }

    public void getSkill() {
        Retro.getRetrofit(context).create(RetroInterface.class).fetchSkill().enqueue(new Callback<List<SkillPojo>>() {
            @Override
            public void onResponse(Call<List<SkillPojo>> call, Response<List<SkillPojo>> response) {
                setSkill(response.body());
            }

            @Override
            public void onFailure(Call<List<SkillPojo>> call, Throwable t) {

            }
        });
    }

    public void setSkill(List<SkillPojo> skillPojo) {
        skillId = new ArrayList<String>(skillPojo.size());
        skillName = new ArrayList<String>(skillPojo.size());


        for (SkillPojo temp : skillPojo) {
            skillId.add(temp.getPrimary_skill_id());
            skillName.add(temp.getPrimary_skill());
        }
        ArrayAdapter setSkillData = new ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, skillName) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                }
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        skillSpinner.setAdapter(setSkillData);
    }

}
