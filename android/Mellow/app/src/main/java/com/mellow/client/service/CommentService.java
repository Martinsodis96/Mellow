package com.mellow.client.service;

import android.content.Context;

import com.mellow.client.api.CommentApi;
import com.mellow.mellow.R;
import com.mellow.model.Comment;

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

public class CommentService extends Service{

    private CommentApi commentApi;
    private ExecutorService executor = Executors.newCachedThreadPool();

    public CommentService(Context context) {
        super(context);
        commentApi = getRetrofit().create(CommentApi.class);
    }

    //TODO add serious exception handling
    public List<Comment> getAllComments(final Long postId) {
        Future<List<Comment>> future = executor.submit(new Callable<List<Comment>>() {
            @Override
            public List<Comment> call() throws Exception {
                try {
                    Response response = commentApi.getAllComments(postId).execute();
                    if (response.isSuccessful()) {
                        saveAuthenticationToken(response);
                        return (List<Comment>) response.body();
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

    public Response createComment(final Comment comment, final Long postId) {
        Future<Response> future = executor.submit(new Callable<Response>() {
            @Override
            public Response call() throws Exception {
                try {
                    Response response = commentApi.createComment(postId, comment).execute();
                    if (response.isSuccessful()) {
                        saveAuthenticationToken(response);
                        return response;
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
            return null;
        }
    }

}
