package com.mellow.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mellow.client.adapter.UserAdapter;
import com.mellow.mellow.R;
import com.mellow.model.User;

import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private UserAdapter userAdapter;
    Button loginButton;
    EditText usernameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.loginButton = (Button) findViewById(R.id.login_button);
        this.usernameInput = (EditText) findViewById(R.id.username_input);
        this.userAdapter = new UserAdapter(this);
    }

    public void onLoginClicked(View view) {
        if (!usernameInput.getText().toString().isEmpty()) {
            Response response = userAdapter.createUser(new User(usernameInput.getText().toString()));
            if(response.isSuccessful()){
                saveLoggedIn(true, response.headers().get("location"));
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                //TODO display to the user that there is something wrong with the username or connection
                System.out.println("failed to create user");
            }
        }
    }

    private void saveLoggedIn(boolean value, String url) {
        User createUser = userAdapter.getUserByUrl(url);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", value);
        editor.putLong("userId", createUser.getId());
        editor.apply();
    }
}