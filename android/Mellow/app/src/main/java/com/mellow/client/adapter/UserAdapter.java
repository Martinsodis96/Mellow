package com.mellow.client.adapter;

import android.content.res.Resources;

import com.mellow.client.api.UserApi;
import com.mellow.mellow.R;
import com.mellow.model.User;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAdapter {

    private final String MELLOW_BASE_URL = Resources.getSystem().getString(R.string.MELLOW_BASE_URL);
    private UserApi userApi;
    private Retrofit retrofit;
    private ExecutorService executor = Executors.newCachedThreadPool();

    public UserAdapter() {
        retrofit = new Retrofit.Builder().baseUrl(MELLOW_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        userApi = retrofit.create(UserApi.class);
    }

    //TODO add serious exception handling
    public Response createUser(final User user) {
        Future<Response> future = executor.submit(new Callable<Response>() {
            @Override
            public Response call() throws Exception {
                try {
                    return userApi.createUser(user).execute();
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
            return null;
        }
    }

    //TODO add serious exception handling
    public User getUserByUrl(final String url) {
        Future<User> future = executor.submit(new Callable<User>() {
            @Override
            public User call() throws Exception {
                try {
                    Response response = userApi.getUserByUrl(url).execute();
                    if(response.isSuccessful()){
                        return (User) response.body();
                    }else{
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
            return null;
        }
    }

}
