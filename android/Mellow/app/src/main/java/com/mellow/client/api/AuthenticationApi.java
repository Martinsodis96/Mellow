package com.mellow.client.api;

import com.mellow.model.Credentials;
import com.mellow.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenticationApi {

    @POST("login")
    Call<User> login(@Body Credentials credentials);

    @POST("register")
    Call<Void> register(@Body Credentials credentials);

}
