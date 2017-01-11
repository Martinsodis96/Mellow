package com.mellow.client.api;

import com.mellow.model.Post;
import com.mellow.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface UserApi {

    @POST("users")
    Call<Void> createUser(@Body User user);

    @GET
    Call<User> getUserByUrl(@Url String url);

    @GET("users/{userId}")
    Call<User> getUserById(@Path("userId") Long userId);

    @GET("users/{userId}/posts")
    Call<List<Post>> getPostsFromUsers(@Path("userId") Long userId);
}
