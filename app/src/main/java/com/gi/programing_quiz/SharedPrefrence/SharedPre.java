package com.gi.programing_quiz.SharedPrefrence;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Keep;

import com.gi.programing_quiz.R;


@Keep
public class SharedPre
{
    public static String name;
    Context c;

    public SharedPre(Context c)
    {
        this.c = c;
        name = c.getString(R.string.shared_file);
    }
    public void writeData(String key,String value)
    {
        SharedPreferences pre = c.getSharedPreferences(name,c.MODE_PRIVATE);
        SharedPreferences.Editor ed = pre.edit();
        ed.putString(key,value);
        ed.commit();
    }
    public String readData(String key,String def)
    {
        SharedPreferences pre = c.getSharedPreferences(name,c.MODE_PRIVATE);
        String data = pre.getString(key,def);
        return data;
    }
}