package com.mellow.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mellow.adapter.CommentArrayAdapter;
import com.mellow.adapter.FlowArrayAdapter;
import com.mellow.client.adapter.CommentAdapter;
import com.mellow.client.adapter.UserAdapter;
import com.mellow.mellow.R;
import com.mellow.model.Comment;
import com.mellow.model.Post;
import com.mellow.model.User;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity {

    ListView commentListView;
    ArrayAdapter adapter;
    TextView username;
    TextView contentText;
    TextView commentContent;
    private Post post;
    private CommentAdapter commentAdapter;
    private UserAdapter userAdapter;
    private List<Comment> comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        setTitle(R.string.title_activity_post);
        this.commentAdapter = new CommentAdapter(this);
        this.userAdapter = new UserAdapter(this);
        this.commentContent = (TextView) findViewById(R.id.comment_content);
        this.post = new Post(getIntent().getStringExtra("post_content"))
                .setId(getIntent().getLongExtra("postId", 1L))
                .setUser(new User(getIntent().getStringExtra("username")));
        setUpUsernameLayout();
        setUpContentTextLayout();
        this.comments = commentAdapter.getAllComments(post.getId());
        setUpCommentListView(comments);
    }

    public void onCommentClicked(View view){
        if(!commentContent.getText().toString().isEmpty()){
            User user = userAdapter.getUserById(getUserId(this));
            Comment comment = new Comment(commentContent.getText().toString(), user);
            commentAdapter.createComment(comment, post.getId());
            commentContent.setText("");
            comments.add(comment);
            adapter.notifyDataSetChanged();
            commentListView.smoothScrollToPosition(comments.size());
        }
    }

    private void setUpCommentListView(List<Comment> comments){
        this.commentListView = (ListView) findViewById(R.id.comment_listview);
        adapter = new CommentArrayAdapter(this, comments);
        commentListView.setAdapter(adapter);
        commentListView.setSelection(commentListView.getCount() - 1);
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

    private Long getUserId(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getLong("userId", 1L);
    }
}
