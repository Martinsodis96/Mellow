package com.mellow.client.api;

import com.mellow.model.Like;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface LikeApi {

    @GET("posts/{postId}/likes/{likeId}")
    Call<Like> getLikeById(@Path("postId") Long postId, @Path("likeId") Long likeId);

    @GET
    Call<Like> getLikeByUrl(@Url String url);
}
