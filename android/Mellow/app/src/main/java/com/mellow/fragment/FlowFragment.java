package com.mellow.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.mellow.FlowArrayAdapter;
import com.mellow.client.adapter.PostAdapter;
import com.mellow.mellow.R;
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
        postAdapter = new PostAdapter();
        List<Post> posts = new ArrayList<>();
        posts.add(new Post("This is just some dummy text to show how it will look in the future :) I'll make this a long one just to try.", "Martin", "Söderstrand"));
        posts.add(new Post("I'm bored.", "Oskar", "Rosengård"));
        posts.add(new Post("I'm trying to be able to scroll and that's why i made this post.", "Olle", "Falcon"));
        //adapter = new FlowArrayAdapter(getActivity().getApplicationContext(), postAdapter.getAllPosts());
        adapter = new FlowArrayAdapter(getActivity().getApplicationContext(), posts);
        postListView.setAdapter(adapter);
    }
}
