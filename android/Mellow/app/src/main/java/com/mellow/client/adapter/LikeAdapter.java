package com.mellow.client.adapter;

import android.content.res.Resources;

import com.mellow.client.api.LikeApi;
import com.mellow.client.api.PostApi;
import com.mellow.mellow.R;
import com.mellow.model.Like;
import com.mellow.model.Post;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LikeAdapter {

    private final String MELLOW_BASE_URL = Resources.getSystem().getString(R.string.MELLOW_BASE_URL);
    private LikeApi likeApi;
    private Retrofit retrofit;
    private ExecutorService executor = Executors.newCachedThreadPool();

    public LikeAdapter() {
        retrofit = new Retrofit.Builder().baseUrl(MELLOW_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        likeApi = retrofit.create(LikeApi.class);
    }

    //TODO add serious exception handling
    public Like getLike(final String url) {
        Future<Like> future = executor.submit(new Callable<Like>() {
            @Override
            public Like call() throws Exception {
                try {
                    Response response = likeApi.getLikeByUrl(url).execute();
                    if (response.isSuccessful()) {
                        return (Like) response.body();
                    } else {
                        //TODO display to the user that there is something wrong
                        System.out.println(response.code());
                        throw new IOException();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new IOException(e);
                }
            }
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return new Like(1L);
        }
    }

}
