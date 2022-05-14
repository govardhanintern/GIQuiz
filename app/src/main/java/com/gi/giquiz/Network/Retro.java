package com.gi.giquiz.Network;

import android.content.Context;


import com.gi.giquiz.R;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retro {
    public static Retrofit r = null;

    public static Retrofit getRetrofit(Context context) {
        if (r == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .build();
            Retrofit.Builder builder = new Retrofit.Builder();
            builder.baseUrl(context.getString(R.string.IP));
            builder.addConverterFactory(GsonConverterFactory.create());
            builder.client(okHttpClient);
            r = builder.build();
        }
        return r;
    }
}
