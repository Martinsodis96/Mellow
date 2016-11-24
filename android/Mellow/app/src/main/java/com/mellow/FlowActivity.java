package com.mellow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.mellow.mellow.R;
import com.mellow.model.Post;

import java.util.ArrayList;
import java.util.List;

public class FlowActivity extends AppCompatActivity {

    ListView postListView;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow);
        setTitle("Flow");
        postListView = (ListView) findViewById(R.id.postListView);

        List<Post> posts = new ArrayList<>();
        posts.add(new Post("This is just a temp text to show how it will look in the future1!", "Test1", "Testsson1"));
        posts.add(new Post("This is just a temp text to show how it will look in the future2!", "Test2", "Testsson2"));
        posts.add(new Post("This is just a temp text to show how it will look in the future3!", "Test3", "Testsson3"));
        posts.add(new Post("This is just a temp text to show how it will look in the future4!", "Test4", "Testsson4"));
        adapter = new CustomAdapterActivity(this, posts);
        postListView.setAdapter(adapter);

    }

    public void onFlowClicked(View view){
        //TODO update the flow
    }
}
