package com.mellow.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mellow.adapter.MessageArrayAdapter;
import com.mellow.mellow.R;
import com.mellow.model.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private Button sendButton;
    private EditText messageInput;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter messageAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<Message> messages;
    private SharedPreferences sharedPreferences;
    private DatabaseReference databaseReference;
    private DatabaseReference messagesReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initializePalettes();
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
        this.messagesReference = databaseReference.child("messages");
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.messages = new ArrayList<>();
        setupRecycleView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        messagesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Message> newMessages = new ArrayList<>();
                for(DataSnapshot childDataSnapshot : dataSnapshot.getChildren()){
                    newMessages.add(childDataSnapshot.getValue(Message.class));
                }
                messages.clear();
                messages.addAll(newMessages);
                if(!messages.isEmpty())
                    recyclerView.smoothScrollToPosition(messages.size() - 1);
                messageAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void setupRecycleView(){
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        messageAdapter = new MessageArrayAdapter(messages, this);
        recyclerView.setAdapter(messageAdapter);
    }

    public void onSendClicked(View view) {
        List<Message> newMessages = new ArrayList<>();
        newMessages.addAll(messages);
        newMessages.add(new Message(messageInput.getText().toString(), sharedPreferences.getString("username", "")));
        messagesReference.setValue(newMessages);
        messageInput.setText("");
    }

    private void initializePalettes() {
        this.sendButton = findViewById(R.id.send_button);
        this.messageInput = findViewById(R.id.message_input);
        this.recyclerView = findViewById(R.id.messages_list);
    }
}
