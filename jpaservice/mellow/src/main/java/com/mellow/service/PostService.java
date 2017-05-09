package com.mellow.service;

import com.mellow.entity.model.CommentModel;
import com.mellow.entity.model.LikeModel;
import com.mellow.entity.model.PostModel;
import com.mellow.entity.model.UserModel;
import com.mellow.repository.CommentRepository;
import com.mellow.repository.LikeRepository;
import com.mellow.repository.PostRepository;
import com.mellow.repository.UserRepository;
import com.mellow.service.exception.DatabaseException;
import com.mellow.service.exception.InvalidInputException;
import com.mellow.service.exception.NoSearchResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;
    private CommentRepository commentRepository;
    private LikeRepository likeRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository,
                       CommentRepository commentRepository, LikeRepository likeRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
    }

    public PostModel getPostById(Long postId) {
        return execute(postRepository1 -> postRepository1.findOne(postId),
                "Failed to get post with id: " + postId);
    }

    public Iterable<PostModel> getAllPosts() {
        try {
            return postRepository.findAll();
        } catch (DataAccessException e) {
            throw new DatabaseException("Failed to get all posts");
        }
    }

    public PostModel createPost(Long userId, String content) {
        if (content != null || userId != null) {
            return execute(postRepository -> {
                UserModel user = userRepository.findOne(userId);
                if (user != null) {
                    return postRepository.save(new PostModel(content, user));
                } else {
                    throw new NoSearchResultException("Could not find user with id: " + userId);
                }
            }, "Failed to create post");
        } else {
            throw new InvalidInputException("Post content or userId can't be null");
        }
    }

    public PostModel updatePost(Long postId, String content) {
        if (content != null || postId != null) {
            return execute(postRepository -> {
                PostModel post = postRepository.findOne(postId);
                if (post != null) {
                    return postRepository.save(post.setContent(content));
                } else {
                    throw new NoSearchResultException("Could not find post with id: " + postId);
                }
            }, "Failed to update post with id: " + postId);
        } else {
            throw new InvalidInputException("Post content or id can't be null");
        }
    }

    public PostModel removePost(Long postId) {
        return execute(postRepository -> {
            PostModel post = postRepository.findOne(postId);
            if (post != null) {
                postRepository.delete(postId);
                return post;
            } else {
                throw new NoSearchResultException("Could not find post with id: " + postId);
            }
        }, "Failed to remove post with id: " + postId);
    }

    public List<CommentModel> getAllCommentsFromPost(Long postId) {
        try {
            PostModel postModel = postRepository.findOne(postId);
            if (postModel != null) {
                return commentRepository.findByPostId(postId);
            } else {
                throw new NoSearchResultException("Could not find post" +
                        " with id: " + postId);
            }
        } catch (DataAccessException e) {
            throw new DatabaseException("Failed to get all comments");
        }
    }

    public List<LikeModel> getAllLikesFromPost(Long postId) {
        try {
            PostModel postModel = postRepository.findOne(postId);
            if (postModel != null) {
                return likeRepository.findByPostId(postId);
            } else {
                throw new NoSearchResultException("Could not find post" +
                        " with id: " + postId);
            }
        } catch (DataAccessException e) {
            throw new DatabaseException("Failed to get all likes");
        }
    }

    public LikeModel addLikeToPost(Long postId, Long userId) {
        try {
            PostModel postModel = postRepository.findOne(postId);
            UserModel userModel = userRepository.findOne(userId);
            if (postModel != null) {
                if (userModel != null) {
                    return likeRepository.save(new LikeModel(postModel, userModel));
                } else {
                    throw new NoSearchResultException("Could not find user" +
                            " with id: " + userId);
                }
            } else {
                throw new NoSearchResultException("Could not find post" +
                        " with id: " + postId);
            }
        } catch (DataAccessException e) {
            throw new DatabaseException("Failed to add like to post");
        }
    }

    public LikeModel removeLikeFromPost(Long likeId) {
        try {
            LikeModel like = likeRepository.findOne(likeId);
            if (like != null) {
                likeRepository.delete(likeId);
                return like;
            } else {
                throw new NoSearchResultException("Could not find like" +
                        " with id: " + likeId);
            }
        } catch (DataAccessException e) {
            throw new DatabaseException("Failed to remove like from post");
        }
    }

    private PostModel execute(Function<PostRepository, PostModel> operation, String dbExMsg) {
        try {
            return operation.apply(postRepository);
        } catch (DataAccessException e) {
            throw new DatabaseException(dbExMsg);
        }
    }
}