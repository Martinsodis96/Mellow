package com.mellow.activity;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

import com.mellow.adapter.CustomDialogClass;
import com.mellow.client.adapter.PostAdapter;
import com.mellow.mellow.R;
import com.mellow.model.Post;

public class CreatePostActivity extends AppCompatActivity {

    EditText postInput;
    private PostAdapter postAdapter;
    private Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.postInput = (EditText) findViewById(R.id.post_input);
        this.postAdapter = new PostAdapter(this);
        this.userId = getUserId(this);
        postInput.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                if(!postInput.getText().toString().isEmpty()){
                    CustomDialogClass customDialogClass = new CustomDialogClass(this);
                    customDialogClass.show();
                    customDialogClass.getWindow().setAttributes(getNewWidthParam(customDialogClass));
                } else {
                    NavUtils.navigateUpFromSameTask(this);
                }
            return true;
            }

            case R.id.post: {
                if (!postInput.getText().toString().isEmpty()) {
                    postAdapter.createPost(new Post(postInput.getText().toString()), userId);
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private WindowManager.LayoutParams getNewWidthParam(CustomDialogClass customDialogClass) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(customDialogClass.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        return layoutParams;
    }

    @Override
    public void onBackPressed() {
        if (!postInput.getText().toString().isEmpty()) {
            CustomDialogClass customDialogClass = new CustomDialogClass(this);
            customDialogClass.show();
            customDialogClass.getWindow().setAttributes(getNewWidthParam(customDialogClass));
        } else {
            NavUtils.navigateUpFromSameTask(this);
        }
    }

    private Long getUserId(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getLong("userId", 1L);
    }
}