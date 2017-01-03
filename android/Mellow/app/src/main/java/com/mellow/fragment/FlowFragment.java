package com.mellow.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.mellow.CreatePostActivity;
import com.mellow.FlowArrayAdapter;
import com.mellow.client.adapter.PostAdapter;
import com.mellow.mellow.R;
import com.mellow.model.Like;
import com.mellow.model.Post;

import java.util.ArrayList;
import java.util.List;

public class FlowFragment extends Fragment {

    ListView postListView;
    private ListAdapter adapter;
    private PostAdapter postAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_flow, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        postListView = (ListView) view.findViewById(R.id.post_listview);
        makeFloatingActionButtonClickable((FloatingActionButton) view.findViewById(R.id.floating_action_button),
                view.getContext());
        postAdapter = new PostAdapter();
        List<Post> posts = new ArrayList<>();
        List<Like> likes = new ArrayList<>();
        likes.add(new Like(1L, 4L));
        likes.add(new Like(1L, 4L));
        posts.add(new Post("This is just some dummy text to show how it will look in the future :) I'll make this a long one just to try.", "Martin", "Söderstrand"));
        posts.add(new Post("I'm bored.", "Oskar", "Rosengård"));
        posts.add(new Post("I'm trying to be able to scroll and that's why i made this post.", "Olle", "Falcon")
        .setLikes(likes));
        //adapter = new FlowArrayAdapter(getActivity().getApplicationContext(), postAdapter.getAllPosts());
        adapter = new FlowArrayAdapter(getActivity().getApplicationContext(), posts);
        postListView.setAdapter(adapter);
    }

    public void makeFloatingActionButtonClickable(FloatingActionButton floatingActionButton, final Context context){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, CreatePostActivity.class);
                startActivity(intent);
            }
        });
    }
}
