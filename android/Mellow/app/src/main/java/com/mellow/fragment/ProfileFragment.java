package com.mellow.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mellow.adapter.FlowArrayAdapter;
import com.mellow.client.adapter.PostAdapter;
import com.mellow.mellow.R;
import com.mellow.model.Post;

import java.util.Collections;
import java.util.List;

public class ProfileFragment extends Fragment {

    ListView profileListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpListView(view, savedInstanceState);
    }

    private void setUpListView(View view, Bundle bundle){
        profileListView = (ListView) view.findViewById(R.id.profile_list_view);
        LayoutInflater infalter = getLayoutInflater(bundle);
        ViewGroup header = (ViewGroup) infalter.inflate(R.layout.content_profile_information, profileListView, false);
        profileListView.addHeaderView(header);
        PostAdapter postAdapter = new PostAdapter(view.getContext());
        List<Post> posts = postAdapter.getAllPosts();
        Collections.reverse(posts);
        ArrayAdapter adapter = new FlowArrayAdapter(getActivity().getApplicationContext(),posts);
        profileListView.setAdapter(adapter);
    }
}
