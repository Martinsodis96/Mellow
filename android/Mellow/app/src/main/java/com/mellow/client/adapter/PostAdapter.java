package com.mellow.client.adapter;

import com.mellow.client.api.PostApi;
import com.mellow.model.Post;
import com.mellow.model.User;

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

public class PostAdapter {

    private final String MELLOW_BASE_URL = "http://192.168.1.95:8080/";
    private PostApi postApi;
    private Retrofit retrofit;
    private ExecutorService executor = Executors.newCachedThreadPool();

    public PostAdapter() {
        retrofit = new Retrofit.Builder().baseUrl(MELLOW_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        postApi = retrofit.create(PostApi.class);
    }

    //TODO add serious exception handling
    public List<Post> getAllPosts(){
        Future<List<Post>> future = executor.submit(new Callable<List<Post>>() {
            @Override
            public List<Post> call() throws Exception {
                try {
                    Response response = postApi.getAllPosts().execute();
                    System.out.println(response.message());
                    return (List<Post>) response.body();
                } catch (IOException e) {
                    throw new IOException(e);
                }
            }
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void createPost(final Post post, final Long userId){
        executor.submit(new Callable<Post>() {
            @Override
            public Post call() throws Exception {
                try {
                    Response response = postApi.createPost(userId, post).execute();
                    System.out.println(response.code());
                    return new Post("This is a new post");
                } catch (IOException e) {
                    throw new IOException(e);
                }
            }
        });
    }
}
