package com.mellow.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.mellow.adapter.CommentArrayAdapter;
import com.mellow.adapter.FlowArrayAdapter;
import com.mellow.mellow.R;
import com.mellow.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity {

    ListView commentListView;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        setTitle(R.string.title_activity_post);
        //TODO get comments from the database using CommentAdapter and CommentAPI
        setUpCommentListView(new ArrayList<Comment>());
    }

    private void setUpCommentListView(List<Comment> comments){
        this.commentListView = (ListView) findViewById(R.id.comment_listview);
        adapter = new CommentArrayAdapter(this, comments);
        commentListView.setAdapter(adapter);
    }
}
