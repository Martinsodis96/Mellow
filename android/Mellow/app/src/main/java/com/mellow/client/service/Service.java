package com.mellow.client.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.mellow.mellow.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class Service {

    private final ExecutorService executor;
    private final Retrofit retrofit;
    private final Context context;

    public Service(Context context) {
        OkHttpClient okHttpBuilder = new OkHttpClient.Builder().addInterceptor(new ServiceInterceptor(context)).build();
        this.executor = Executors.newCachedThreadPool();
        this.context = context;
        String MELLOW_BASE_URL = context.getResources().getString(R.string.MELLOW_BASE_URL);
        retrofit = new Retrofit.Builder()
                .baseUrl(MELLOW_BASE_URL)
                .client(okHttpBuilder)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    protected void saveAuthenticationToken(Response response) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
        editor.putString("access_token", response.headers().get("access_token"));
        editor.apply();
    }

    protected Retrofit getRetrofit() {
        return this.retrofit;
    }

    protected ExecutorService getExecutor() {
        return this.executor;
    }

    protected Context getContext(){
        return this.context;
    }

}
