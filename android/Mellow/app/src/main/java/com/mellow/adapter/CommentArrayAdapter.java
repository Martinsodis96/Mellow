package com.mellow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.mellow.mellow.R;
import com.mellow.model.Comment;

import java.util.List;

public class CommentArrayAdapter extends ArrayAdapter<Comment> {

    public CommentArrayAdapter(Context context, List<Comment> comments) {
        super(context, R.layout.activity_comment_adapter, comments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.activity_comment_adapter, parent, false);

        return customView;
    }
}
