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
import com.mellow.mellow.R;
import com.mellow.model.Post;

import java.util.ArrayList;
import java.util.List;

public class FlowFragment extends Fragment {

    ListView postListView;
    private ListAdapter adapter;

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

        List<Post> posts = new ArrayList<>();
        posts.add(new Post("This is just a temp text!", "Test1", "Testsson1"));
        posts.add(new Post("This is a longer temp text to show how it will look in the future when someone type something longer!", "Test2", "Testsson2"));
        posts.add(new Post("This is just a temp text to show how it will look in the future3!", "Test3", "Testsson3"));
        posts.add(new Post("This is just a temp text to show how it will look in the future4!", "Test4", "Testsson4"));
        adapter = new CustomArrayAdapter(getActivity().getApplicationContext(), posts);
        postListView.setAdapter(adapter);
    }
}
