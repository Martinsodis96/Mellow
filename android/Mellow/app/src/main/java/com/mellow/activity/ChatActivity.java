package com.mellow.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mellow.adapter.CustomDialogClass;
import com.mellow.adapter.MessageArrayAdapter;
import com.mellow.mellow.R;
import com.mellow.model.Message;
import com.mellow.model.Post;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private Button sendButton;
    private EditText messageInput;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<Message> messages;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initializePalettes();
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mRecyclerView = findViewById(R.id.messages_list);
        mRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        this.messages = new ArrayList<>();
        messages.add(new Message("This is a message", "someone"));
        messages.add(new Message("This is another message", "user"));
        messages.add(new Message("This is an even longer message that creates multiple rows just to see what it looks like", "user"));
        mAdapter = new MessageArrayAdapter(messages, this);
        mRecyclerView.setAdapter(mAdapter);
    }

/*    @Override
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
                    postService.createPost(new Post(postInput.getText().toString()), userId);
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }*/

    public void onSendClicked(View view) {
        messages.add(new Message(messageInput.getText().toString(), sharedPreferences.getString("username", "")));
        mAdapter.notifyDataSetChanged();
        mRecyclerView.smoothScrollToPosition(messages.size()-1);
        messageInput.setText("");
    }

    private void initializePalettes() {
        this.sendButton = findViewById(R.id.send_button);
        this.messageInput = findViewById(R.id.message_input);
    }
}
