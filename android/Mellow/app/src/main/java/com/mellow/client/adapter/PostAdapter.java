package com.mellow.client.adapter;

import com.mellow.client.api.PostApi;
import com.mellow.model.Post;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostAdapter {

    private final String MELLOW_BASE_URL = "http://192.168.0.16:8080/mellow/";
    private PostApi postApi;
    private Retrofit retrofit;

    public PostAdapter() {
        retrofit = new Retrofit.Builder().baseUrl(MELLOW_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        postApi = retrofit.create(PostApi.class);
    }

    public void getAllPosts(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = postApi.getAllPosts().execute();
                    System.out.println(response.code());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
    }
}
