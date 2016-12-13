package com.mellow.client.api;

import com.mellow.model.Post;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostApi {

    @GET("posts")
    Call<Post> getAllPosts();
}
