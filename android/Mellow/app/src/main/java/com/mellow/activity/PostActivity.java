package com.mellow.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mellow.adapter.CommentArrayAdapter;
import com.mellow.adapter.FlowArrayAdapter;
import com.mellow.mellow.R;
import com.mellow.model.Comment;
import com.mellow.model.Post;
import com.mellow.model.User;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity {

    ListView commentListView;
    ListAdapter adapter;
    TextView username;
    TextView contentText;
    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        setTitle(R.string.title_activity_post);

        this.post = new Post(getIntent().getStringExtra("post_content"))
                .setId(getIntent().getLongExtra("postId", 1L))
                .setUser(new User(getIntent().getStringExtra("username")));
        setUpUsernameLayout();
        setUpContentTextLayout();
        //TODO get comments from the database using CommentAdapter and CommentAPI
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment("This is a long comment so that you can see how till will look. This is a long comment so that you can see how till will look."));
        comments.add(new Comment("This is a long comment so."));
        comments.add(new Comment("You're so dumb"));
        setUpCommentListView(comments);
    }

    private void setUpCommentListView(List<Comment> comments){
        this.commentListView = (ListView) findViewById(R.id.comment_listview);
        adapter = new CommentArrayAdapter(this, comments);
        commentListView.setAdapter(adapter);
    }

    private void setUpUsernameLayout(){
        this.username = (TextView) findViewById(R.id.username);
        if (username != null) {
            username.setText(post.getUser().getUsername());
            username.setTextSize(21);
        }
    }

    private void setUpContentTextLayout(){
        this.contentText = (TextView) findViewById(R.id.content_text);
        if(contentText != null){
            contentText.setText(post.getContent());
            contentText.setTextSize(17);
        }
    }
}
