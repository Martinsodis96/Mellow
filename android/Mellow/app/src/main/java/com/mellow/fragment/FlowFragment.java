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

import com.mellow.activity.CreatePostActivity;
import com.mellow.adapter.FlowArrayAdapter;
import com.mellow.client.service.PostService;
import com.mellow.mellow.R;
import com.mellow.model.Post;

import java.util.Collections;
import java.util.List;

public class FlowFragment extends Fragment {

    ListView postListView;
    private ListAdapter adapter;
    private PostService postService;

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
        postService = new PostService(view.getContext());
        List<Post> posts = postService.getAllPosts();
        Collections.reverse(posts);
        adapter = new FlowArrayAdapter(getActivity().getApplicationContext(),posts);
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
