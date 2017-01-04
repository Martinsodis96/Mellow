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

import com.mellow.mellow.R;
import com.mellow.model.Post;

import java.util.List;

public class FlowArrayAdapter extends ArrayAdapter<Post> {

    private List<Post> posts;
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

        Post post = posts.get(position);
        contentText.setText(post.getContentText());
        if(post.getUser() != null){
            this.usernameTextView.setText(post.getUser().getUsername());
        }
        if(post.getLikes() != null && !post.getLikes().isEmpty()){
            informationContainer.setVisibility(View.VISIBLE);
            amountOfLikes.setText(String.valueOf(post.getLikes().size()));
        }
        setLikeOnClick(likeButton, position, amountOfLikes);
        return customView;
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

    private void setLikeOnClick(Button likeButton, final int position, final TextView amountOfLikes){
        final LinearLayout informationContainer = this.informationContainer;
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(!informationContainer.isShown()){
                    informationContainer.setVisibility(View.VISIBLE);
                }
                int newAmountOfLikes = Integer.valueOf(amountOfLikes.getText().toString()) + 1;
                amountOfLikes.setText(String.valueOf(newAmountOfLikes));
                //TODO Try to save like in the database and don't update gui if like already exist.
            }
        });
    }
}
