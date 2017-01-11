package com.mellow.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mellow.client.adapter.UserAdapter;
import com.mellow.mellow.R;
import com.mellow.model.User;

import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private UserAdapter userAdapter;
    EditText usernameInput;
    TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.usernameInput = (EditText) findViewById(R.id.username_input);
        this.errorMessage = (TextView) findViewById(R.id.error_message);
        this.userAdapter = new UserAdapter(this);
    }

    public void onLoginClicked(View view) {
        if (!usernameInput.getText().toString().isEmpty()) {
            if (!usernameTooShort()) {
                Response response = userAdapter.createUser(new User(usernameInput.getText().toString()));
                if (response.isSuccessful()) {
                    saveLoggedIn(true, response.headers().get("location"));
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    errorMessage.setText("");
                    finish();
                } else {
                    errorMessage.setText(R.string.username_already_taken);
                }
            } else {
                errorMessage.setText(R.string.username_too_short);
            }
        }
    }

    private boolean usernameTooShort() {
        return usernameInput.getText().toString().length() < 4;
    }

    private void saveLoggedIn(boolean value, String url) {
        User createUser = userAdapter.getUserByUrl(url);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", value);
        editor.putLong("userId", createUser.getId());
        editor.putString("username", createUser.getUsername());
        editor.commit();
    }
}