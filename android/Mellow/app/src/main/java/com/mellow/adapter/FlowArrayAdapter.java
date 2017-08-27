package com.mellow.adapter;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mellow.activity.CommentActivity;
import com.mellow.client.service.LikeService;
import com.mellow.client.service.PostService;
import com.mellow.mellow.R;
import com.mellow.model.Like;
import com.mellow.model.Post;

import java.util.List;

import retrofit2.Response;

public class FlowArrayAdapter extends ArrayAdapter<Post> {

    private List<Post> posts;
    private PostService postService;
    private LikeService likeService;
    private Long userId;
    private Like userLike;
    private Context context;
    private ImageView profilePicture;
    private Button likeButton;
    private Button commentButton;
    private Button shareButton;
    private TextView contentText;
    private TextView usernameTextView;
    private TextView amountOfLikes;
    private TextView amountOfComments;
    private LinearLayout informationContainer;
    private LinearLayout likesContainer;
    private LinearLayout commentsContainer;

    public FlowArrayAdapter(Context context, List<Post> posts) {
        super(context, R.layout.activity_flow_adapter, posts);
        this.posts=posts;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.activity_flow_adapter, parent, false);
        initializePalettes(customView);
        this.postService = new PostService(customView.getContext());
        this.likeService = new LikeService(customView.getContext());
        this.userId = getUserId(customView.getContext());
        Post post = posts.get(position);
        contentText.setText(post.getContent());
        setUsernameTextView(post);
        showLikes(post);
        showComments(post);
        setLikeOnClick(likeButton, post, amountOfLikes);
        setCommentOnClick(commentButton, customView.getContext(), post);
        return customView;
    }

    private void setCommentOnClick(Button commentButton, final Context context, final Post post){
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startPostActivity = new Intent(context, CommentActivity.class);
                startPostActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startPostActivity.putExtra("postId", post.getId());
                startPostActivity.putExtra("username", post.getUser().getUsername());
                startPostActivity.putExtra("post_content", post.getContent());
                context.startActivity(startPostActivity);
            }
        });
    }

    private void setLikeOnClick(Button likeButton, final Post post, final TextView amountOfLikes){
        final LinearLayout informationContainer = this.informationContainer;
        final LinearLayout likesContainer = this.likesContainer;
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (likedByUser(post, userId)) {
                    postService.removeLikeFromPost(post.getId(), userLike.getId());
                    post.getLikes().remove(userLike);
                    amountOfLikes.setText(String.valueOf(post.getLikes().size()));
                    if (!hasLikes(post)) {
                        likesContainer.setVisibility(View.INVISIBLE);
                        if(!hasComments(post)){
                            informationContainer.setVisibility(View.GONE);
                        }
                    }
                } else {
                    Response response = postService.addLikeToPost(post.getId(), new Like(userId));
                    Like like = likeService.getLike(response.headers().get("location"));
                    post.getLikes().add(like);
                    amountOfLikes.setText(String.valueOf(post.getLikes().size()));
                    likesContainer.setVisibility(View.VISIBLE);
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

    private boolean hasComments(Post post){
        return post.getComments() != null && !post.getComments().isEmpty();
    }

    private void showLikes(Post post){
        if(hasLikes(post)){
            informationContainer.setVisibility(View.VISIBLE);
            likesContainer.setVisibility(View.VISIBLE);
            amountOfLikes.setText(String.valueOf(post.getLikes().size()));
        }
    }

    private void showComments(Post post){
        if(hasComments(post)){
            informationContainer.setVisibility(View.VISIBLE);
            commentsContainer.setVisibility(View.VISIBLE);
            amountOfComments.setText(String.valueOf(post.getComments().size()));
        }
    }

    private void setUsernameTextView(Post post){
        if(post.getUser() != null){
            this.usernameTextView.setText(post.getUser().getUsername());
        }
    }

    private void initializePalettes(View view){
        this.profilePicture = view.findViewById(R.id.profile_picture);
        this.likeButton = view.findViewById(R.id.like_button);
        this.commentButton = view.findViewById(R.id.comment_button);
        this.shareButton = view.findViewById(R.id.share_button);
        this.contentText = view.findViewById(R.id.content_text);
        this.usernameTextView = view.findViewById(R.id.username);
        this.informationContainer = view.findViewById(R.id.information_container);
        this.amountOfLikes = view.findViewById(R.id.amount_of_likes);
        this.amountOfComments = view.findViewById(R.id.amount_of_comments);
        this.likesContainer = view.findViewById(R.id.likes_container);
        this.commentsContainer = view.findViewById(R.id.comments_container);
    }

    private Long getUserId(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getLong("userId", 1L);
    }
}
