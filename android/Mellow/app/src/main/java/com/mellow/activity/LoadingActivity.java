package com.mellow.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class LoadingActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        forwardBasedOnLoginValue(this);
    }

    private boolean isLoggedIn() {
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

    private void forwardBasedOnLoginValue(final Context context){
        if(isLoggedIn()){
            Intent home_activity = new Intent(context, MainActivity.class);
            startActivity(home_activity);
        }else{
            Intent login_activity = new Intent(context, LoginActivity.class);
            startActivity(login_activity);
        }
        finish();
    }
}
