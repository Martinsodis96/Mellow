package com.mellow.client.api;

import com.mellow.model.Comment;

import java.util.List;

import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CommentApi {

    @GET("posts/{postId}/comments")
    Call<List<Comment>> getAllComments(@Path("postId") Long postId);

    @POST("posts/{postId}/comments")
    Call<Void> createComment(@Path("postId") Long postId, @Body Comment comment);
}
