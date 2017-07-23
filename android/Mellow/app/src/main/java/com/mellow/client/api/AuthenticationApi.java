package com.mellow.client.api;

import com.mellow.model.Credentials;
import com.mellow.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthenticationApi {

    @POST("login")
    @Headers("No-Authentication: true")
    Call<User> login(@Body Credentials credentials);

    @POST("register")
    @Headers("No-Authentication: true")
    Call<Void> register(@Body Credentials credentials);

}
