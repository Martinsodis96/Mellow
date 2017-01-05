package com.mellow.client.adapter;

import com.mellow.client.api.PostApi;
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

public class PostAdapter {

    private final String MELLOW_BASE_URL = "http://192.168.0.16:8080/";
    private PostApi postApi;
    private Retrofit retrofit;
    private ExecutorService executor = Executors.newCachedThreadPool();

    public PostAdapter() {
        retrofit = new Retrofit.Builder().baseUrl(MELLOW_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        postApi = retrofit.create(PostApi.class);
    }

    //TODO add serious exception handling
    public List<Post> getAllPosts() {
        Future<List<Post>> future = executor.submit(new Callable<List<Post>>() {
            @Override
            public List<Post> call() throws Exception {
                try {
                    Response response = postApi.getAllPosts().execute();
                    if (response.isSuccessful()) {
                        return (List<Post>) response.body();
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
            return new ArrayList<>();
        }
    }

    public void createPost(final Post post, final Long userId) {
        executor.submit(new Callable<Response>() {
            @Override
            public Response call() throws Exception {
                try {
                    Response response = postApi.createPost(userId, post).execute();
                    if(response.isSuccessful()){
                        return response;
                    }else{
                        //TODO display to the user that there is something wrong
                        throw new IOException();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new IOException(e);
                }
            }
        });
    }

    public void addLikeToPost(final Long postId, final Like like) {
        executor.submit(new Callable<Response>() {
            @Override
            public Response call() throws Exception {
                try {
                    return postApi.addLikeToPost(postId, like).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new IOException(e);
                }
            }
        });
    }

    public void removeLikeFromPost(final Long postId, final Long likeId) {
        executor.submit(new Callable<Response>() {
            @Override
            public Response call() throws Exception {
                try {
                    Response response = postApi.removeLikeFromPost(postId, likeId).execute();
                    if(response.isSuccessful()){
                        return response;
                    }else{
                        //TODO display to the user that there is something wrong
                        throw new IOException();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new IOException(e);
                }
            }
        });
    }
}
