package com.mellow.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mellow.client.service.AuthenticationService;
import com.mellow.client.service.UserService;
import com.mellow.mellow.R;
import com.mellow.model.Credentials;
import com.mellow.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private AuthenticationService authenticationService;
    private EditText usernameInput;
    private EditText passwordInput;
    private TextView errorMessage;
    private TextView accessType;
    private TextView accessTypeInformation;
    private ProgressBar progressBar;
    private boolean isRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializePalettes();
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
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
            progressBar.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            Call<User> registerCall = authenticationService.login(new Credentials(usernameInput.getText().toString(),
                    passwordInput.getText().toString()));
            registerCall.enqueue(new Callback<User>() {

                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    progressBar.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        authenticationService.saveAuthenticationToken(response);
                        saveLoggedIn(response.body());
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        switch (response.code()) {
                            case 400: {
                                errorMessage.setText(getResources().getString(R.string.invalid_username, usernameInput.getText().toString()));
                                break;
                            }
                            case 401: {
                                errorMessage.setText(R.string.invalid_password);
                                break;
                            }
                        }
                    }
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    errorMessage.setText(R.string.server_connection_error);
                }
            });
        } else {
            errorMessage.setText(R.string.missing_credentials);
        }
    }

    private void register() {
        if (usernameAndPasswordIsPresent()) {
            progressBar.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            Call<Void> registerCall = authenticationService.register(new Credentials(usernameInput.getText().toString(),
                    passwordInput.getText().toString()));
            registerCall.enqueue(new Callback<Void>() {

                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    progressBar.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        authenticationService.saveAuthenticationToken(response);
                        User user = getUserFromLocationHeader(response.headers().get("Location"));
                        saveLoggedIn(user);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        switch (response.code()) {
                            case 400: {
                                errorMessage.setText(R.string.username_already_taken);
                                break;
                            }
                        }
                    }
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    errorMessage.setText(R.string.server_connection_error);
                }
            });
        } else {
            errorMessage.setText(R.string.missing_credentials);
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

    private User getUserFromLocationHeader(String location) {
        String[] urlParts = location.split("/");
        Long id = Long.valueOf(urlParts[urlParts.length - 1]);
        return new UserService(this).getUserById(id);
    }

    private void initializePalettes(){
        this.usernameInput = findViewById(R.id.username_input);
        this.passwordInput = findViewById(R.id.password_input);
        this.errorMessage = findViewById(R.id.error_message);
        this.accessType = findViewById(R.id.access_type_text);
        this.accessTypeInformation = findViewById(R.id.access_type_information);
        this.progressBar = findViewById(R.id.progressBar);
    }
}