package com.mellow.fragment;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mellow.adapter.FlowArrayAdapter;
import com.mellow.client.service.UserService;
import com.mellow.mellow.R;
import com.mellow.model.Post;

import java.util.Collections;
import java.util.List;

public class ProfileFragment extends Fragment {

    ListView profileListView;
    TextView usernameTextView;
    TextView totalAmountOfLikes;
    TextView totalAmountOfPost;
    private UserService userService;
    private List<Post> postsFromUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.userService = new UserService(view.getContext());
        setUpListView(view, savedInstanceState);
        setUpTotalAmountOfLikes(view);
        setUpTotalAmountOfPost(view);
        this.usernameTextView = (TextView) view.findViewById(R.id.username);
        usernameTextView.setText(getUsername(view.getContext()));
    }

    private void setUpListView(View view, Bundle bundle){
        profileListView = (ListView) view.findViewById(R.id.profile_list_view);
        LayoutInflater infalter = getLayoutInflater(bundle);
        ViewGroup header = (ViewGroup) infalter.inflate(R.layout.content_profile_information, profileListView, false);
        profileListView.addHeaderView(header);
        postsFromUser = userService.getPostsFromUser(getUserId(view.getContext()));
        Collections.reverse(postsFromUser);
        ArrayAdapter adapter = new FlowArrayAdapter(getActivity().getApplicationContext(), postsFromUser);
        profileListView.setAdapter(adapter);
    }

    private void setUpTotalAmountOfLikes(View view){
        this.totalAmountOfLikes = (TextView) view.findViewById(R.id.profile_likes);
        totalAmountOfLikes.setText(String.valueOf(getTotalAmountOfLikes(postsFromUser)));
    }

    private int getTotalAmountOfLikes(List<Post> posts){
        int totalAmount = 0;
        for (Post post : posts) {
            totalAmount += post.getLikes().size();
        }
        return totalAmount;
    }

    private void setUpTotalAmountOfPost(View view){
        this.totalAmountOfPost = (TextView) view.findViewById(R.id.profile_posts);
        totalAmountOfPost.setText(String.valueOf(postsFromUser.size()));
    }

    private String getUsername(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("username", "");
    }

    private Long getUserId(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getLong("userId", 1L);
    }
}
