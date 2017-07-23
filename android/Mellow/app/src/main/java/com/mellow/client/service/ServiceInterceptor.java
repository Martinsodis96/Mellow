package com.mellow.client.service;

import android.content.Context;
import android.preference.PreferenceManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ServiceInterceptor implements Interceptor {

    private final Context context;

    public ServiceInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (request.header("No-Authentication") == null) {
            request = request.newBuilder()
                    .addHeader("Authorization", "Bearer " + getAccessToken())
                    .build();

        }
        return chain.proceed(request);
    }

    private String getAccessToken() {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("access_token", "");
    }
}
