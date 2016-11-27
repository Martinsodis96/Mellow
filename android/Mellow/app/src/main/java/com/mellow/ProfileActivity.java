package com.mellow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mellow.mellow.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profile");
    }


    public void onFlowClicked(View view){
        Intent intent = new Intent(this, FlowActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        this.overridePendingTransition(R.animator.slide_in_left, R.animator.slide_out_right);
    }

    public void onChatClicked(View view){
        Intent intent = new Intent(this, ChatActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        this.overridePendingTransition(R.animator.slide_in_left, R.animator.slide_out_right);
    }

}
