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
import com.mellow.client.service.UserService;
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
    private TextView accessType;
    private TextView accessTypeInformation;
    private boolean isRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.usernameInput = (EditText) findViewById(R.id.username_input);
        this.passwordInput = (EditText) findViewById(R.id.password_input);
        this.errorMessage = (TextView) findViewById(R.id.error_message);
        this.accessType = (TextView) findViewById(R.id.access_type_text);
        this.accessTypeInformation = (TextView) findViewById(R.id.access_type_information);
        this.isRegister = true;
        this.authenticationService = new AuthenticationService(this);
    }

    public void onChangeAccessTypeClicked(View view) {
        if (isRegister) {
            isRegister = false;
            setTitle(R.string.title_activity_login);
            accessType.setText(R.string.access_type_login);
            accessTypeInformation.setText(R.string.access_type_information_login);
        } else {
            isRegister = true;
            setTitle(R.string.title_activity_register);
            accessType.setText(R.string.access_type_register);
            accessTypeInformation.setText(R.string.access_type_information_register);
        }
    }

    public void onConfirmClicked(View view) {
        if (isRegister)
            register();
        else
            login();
    }

    private void login() {
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
                default: {
                    errorMessage.setText(R.string.server_connection_error);
                }
            }
        } else {
            //TODO add a error message saying that the input field is empty
        }
    }

    private void register() {
        if (usernameAndPasswordIsPresent()) {
            Response response = authenticationService.register(new Credentials(usernameInput.getText().toString(),
                    passwordInput.getText().toString()));
            switch (response.code()) {
                case 201: {
                    User user = getUserFromLocation(response.headers().get("Location"));
                    saveLoggedIn(user);
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
                case 400: {
                    errorMessage.setText(R.string.username_already_taken);
                    break;
                }
                default: {
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

    private User getUserFromLocation(String location) {
        String[] urlParts = location.split("/");
        Long id = Long.valueOf(urlParts[urlParts.length - 1]);
        return new UserService(this).getUserById(id);
    }
}