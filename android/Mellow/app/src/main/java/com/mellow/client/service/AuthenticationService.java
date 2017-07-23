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

import retrofit2.Response;

public class AuthenticationService extends Service {

    private final AuthenticationApi authenticationApi;

    public AuthenticationService(Context context) {
        super(context);
        authenticationApi = getRetrofit().create(AuthenticationApi.class);
    }

    public Response login(final Credentials credentials) {
        Future<Response> future = getExecutor().submit(new Callable<Response>() {
            @Override
            public Response call() throws Exception {
                try {
                    Response response = authenticationApi.login(credentials).execute();
                    saveAuthenticationToken(response);
                    return response;
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

    public Response register(final Credentials credentials) {
        Future<Response> future = getExecutor().submit(new Callable<Response>() {
            @Override
            public Response call() throws Exception {
                try {
                    return authenticationApi.login(credentials).execute();
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

    protected void saveAuthenticationToken(Response response) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
        editor.putString("access_token", response.headers().get("access_token"));
        editor.putString("refresh_token", response.headers().get("refresh_token"));
        editor.apply();
    }

}
