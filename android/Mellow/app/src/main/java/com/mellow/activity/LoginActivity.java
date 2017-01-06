package com.mellow.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mellow.mellow.R;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    Button loginButton;
    EditText usernameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.loginButton = (Button) findViewById(R.id.login_button);
        this.usernameInput = (EditText) findViewById(R.id.username_input);
    }

    public void onLoginClicked(View view) {
        if (!usernameInput.getText().toString().isEmpty()) {
            saveLoggedIn(true);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void saveLoggedIn(boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", value);
        editor.apply();
    }
}