package com.mellow.client.service;

import android.content.Context;

import com.mellow.mellow.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class Service {

    private final ExecutorService executor;
    private final Retrofit retrofit;

    public Service(Context context) {
        this.executor = Executors.newCachedThreadPool();
        String MELLOW_BASE_URL = context.getResources().getString(R.string.MELLOW_BASE_URL);
        retrofit = new Retrofit.Builder()
                .baseUrl(MELLOW_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    protected Retrofit getRetrofit(){
        return this.retrofit;
    }

    protected ExecutorService getExecutor(){
        return this.executor;
    }

}
