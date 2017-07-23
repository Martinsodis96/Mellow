package com.mellow.client.service;

import android.content.Context;

import com.mellow.client.api.LikeApi;
import com.mellow.mellow.R;
import com.mellow.model.Like;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LikeService extends Service{

    private LikeApi likeApi;
    private ExecutorService executor = Executors.newCachedThreadPool();

    public LikeService(Context context) {
        super(context);
        likeApi = getRetrofit().create(LikeApi.class);
    }

    //TODO add serious exception handling
    public Like getLike(final String url) {
        Future<Like> future = executor.submit(new Callable<Like>() {
            @Override
            public Like call() throws Exception {
                try {
                    Response response = likeApi.getLikeByUrl(url).execute();
                    if (response.isSuccessful()) {
                        saveAuthenticationToken(response);
                        return (Like) response.body();
                    } else {
                        //TODO display to the user that there is something wrong
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
