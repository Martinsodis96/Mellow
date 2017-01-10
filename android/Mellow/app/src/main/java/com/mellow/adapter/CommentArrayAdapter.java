package com.mellow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mellow.mellow.R;
import com.mellow.model.Comment;

import java.util.List;

public class CommentArrayAdapter extends ArrayAdapter<Comment> {

    private List<Comment> comments;
    private Context context;
    TextView usernameTextView;
    TextView commentTextView;

    public CommentArrayAdapter(Context context, List<Comment> comments) {
        super(context, R.layout.activity_comment_adapter, comments);
        this.comments = comments;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.activity_comment_adapter, parent, false);
        this.usernameTextView = (TextView) customView.findViewById(R.id.username_textview);
        this.commentTextView= (TextView) customView.findViewById(R.id.comment_textview);

        Comment comment = comments.get(position);
        commentTextView.setText(comment.getContent());
        if(comment.getUser() != null){
            usernameTextView.setText(comment.getUser().getUsername());
        }
        return customView;
    }
}
