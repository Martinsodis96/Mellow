package com.mellow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mellow.client.adapter.PostAdapter;
import com.mellow.mellow.R;
import com.mellow.model.Like;
import com.mellow.model.Post;
import com.mellow.model.User;

import java.util.List;

import retrofit2.Response;

public class FlowArrayAdapter extends ArrayAdapter<Post> {

    private List<Post> posts;
    private PostAdapter postAdapter;
    private Long userId = 1L;
    private Like userLike;
    ImageView profilePicture;
    Button likeButton;
    Button commentButton;
    Button shareButton;
    TextView contentText;
    TextView usernameTextView;
    TextView amountOfLikes;
    LinearLayout informationContainer;

    public FlowArrayAdapter(Context context, List<Post> posts) {
        super(context, R.layout.activity_flow_adapter, posts);
        this.posts=posts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.activity_flow_adapter, parent, false);
        initializePalettes(customView);
        this.postAdapter = new PostAdapter();
        Post post = posts.get(position);
        contentText.setText(post.getContent());
        setUsernameTextView(post);
        showLikes(post);
        setLikeOnClick(likeButton, post, amountOfLikes);
        return customView;
    }

    private void setLikeOnClick(Button likeButton, final Post post, final TextView amountOfLikes){
        final LinearLayout informationContainer = this.informationContainer;
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (likedByUser(post, userId)) {
                    System.out.println(userLike.getId());
                    postAdapter.removeLikeFromPost(post.getId(), userLike.getId());
                    post.getLikes().remove(userLike);
                    amountOfLikes.setText(String.valueOf(post.getLikes().size()));
                    if (post.getLikes().isEmpty()) {
                        informationContainer.setVisibility(View.GONE);
                    }
                } else {
                    Like newLike = new Like(userId);
                    postAdapter.addLikeToPost(post.getId(), newLike);
                    //TODO get the new like from the database.
                    post.getLikes().add(newLike);
                    amountOfLikes.setText(String.valueOf(post.getLikes().size()));
                    if (!informationContainer.isShown()) {
                        informationContainer.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    private boolean likedByUser(Post post, Long userId){
        for (Like like : post.getLikes()) {
            if(like.getUserId() != null && userId.equals(like.getUserId())){
                this.userLike = like;
                return true;
            }
        }
        return false;
    }

    private boolean hasLikes(Post post){
        return post.getLikes() != null && !post.getLikes().isEmpty();
    }

    private void showLikes(Post post){
        if(hasLikes(post)){
            informationContainer.setVisibility(View.VISIBLE);
            amountOfLikes.setText(String.valueOf(post.getLikes().size()));
        }
    }

    private void setUsernameTextView(Post post){
        if(post.getUser() != null){
            this.usernameTextView.setText(post.getUser().getUsername());
        }
    }

    private void initializePalettes(View view){
        this.profilePicture = (ImageView) view.findViewById(R.id.profile_picture);
        this.likeButton = (Button) view.findViewById(R.id.like_button);
        this.commentButton = (Button) view.findViewById(R.id.comment_button);
        this.shareButton = (Button) view.findViewById(R.id.share_button);
        this.contentText = (TextView) view.findViewById(R.id.content_text);
        this.usernameTextView = (TextView) view.findViewById(R.id.username);
        this.informationContainer = (LinearLayout) view.findViewById(R.id.information_container);
        this.amountOfLikes = (TextView) view.findViewById(R.id.amount_of_likes);
    }
}
