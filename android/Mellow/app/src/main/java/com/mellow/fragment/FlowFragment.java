package com.mellow.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.mellow.CustomArrayAdapter;
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
        adapter = new CustomArrayAdapter(getActivity().getApplicationContext(), postAdapter.getAllPosts());
        postListView.setAdapter(adapter);
    }
}
