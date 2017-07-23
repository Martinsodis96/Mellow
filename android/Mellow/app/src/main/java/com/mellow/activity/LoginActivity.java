package com.mellow.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mellow.client.service.AuthenticationService;
import com.mellow.mellow.R;
import com.mellow.model.Credentials;
import com.mellow.model.User;

import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private AuthenticationService authenticationService;
    private EditText usernameInput;
    private EditText passwordInput;
    private TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.usernameInput = (EditText) findViewById(R.id.username_input);
        this.passwordInput = (EditText) findViewById(R.id.password_input);
        this.errorMessage = (TextView) findViewById(R.id.error_message);
        this.authenticationService = new AuthenticationService(this);
    }

    public void onLoginClicked(View view) {
        if (usernameAndPasswordIsPresent()) {
            Response response = authenticationService.login(new Credentials(usernameInput.getText().toString(),
                    passwordInput.getText().toString()));
            switch (response.code()) {
                case 200: {
                    saveLoggedIn((User) response.body());
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
                case 400: {
                    errorMessage.setText(getResources().getString(R.string.invalid_username, usernameInput.getText().toString()));
                    break;
                }
                case 401: {
                    errorMessage.setText(R.string.invalid_password);
                    break;
                }
                default:{
                    errorMessage.setText(R.string.server_connection_error);
                }
            }
        } else {
            //TODO add a error message saying that the input field is empty
        }
    }

    private boolean usernameAndPasswordIsPresent() {
        return !usernameInput.getText().toString().isEmpty() && !passwordInput.getText().toString().isEmpty();
    }

    private void saveLoggedIn(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putLong("userId", user.getId());
        editor.putString("username", user.getUsername());
        editor.commit();
    }
}