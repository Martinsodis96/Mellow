package com.mellow.client.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.mellow.client.api.AuthenticationApi;
import com.mellow.model.Credentials;
import com.mellow.model.User;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationService extends Service {

    private final AuthenticationApi authenticationApi;

    public AuthenticationService(Context context) {
        super(context);
        authenticationApi = getRetrofit().create(AuthenticationApi.class);
    }

    public Call<User> login(final Credentials credentials) {
        return authenticationApi.login(credentials);
    }

    public Call<Void> register(Credentials credentials){
        return authenticationApi.register(credentials);
    }

    public void saveAuthenticationToken(Response response) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
        editor.putString("access_token", response.headers().get("access_token"));
        editor.putString("refresh_token", response.headers().get("refresh_token"));
        editor.apply();
    }
}
