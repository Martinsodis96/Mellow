package com.mellow.client.api;

import com.mellow.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PostApi {

    @GET("posts")
    Call<List<Post>> getAllPosts();

    @POST("users/{userId}/posts")
    Call<Post> createPost(@Path("userId") Long userId, @Body Post post);
}
