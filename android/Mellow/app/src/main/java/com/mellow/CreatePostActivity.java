package com.mellow;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.mellow.mellow.R;

public class CreatePostActivity extends AppCompatActivity {

    EditText postInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        this.postInput = (EditText) findViewById(R.id.post_input);
        postInput.requestFocus();
        InputMethodManager inputMethodManageran = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
        inputMethodManageran.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

}
