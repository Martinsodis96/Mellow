package com.mellow.service;

import com.mellow.model.CommentModel;
import com.mellow.model.PostModel;
import com.mellow.model.UserModel;
import com.mellow.repository.CommentRepository;
import com.mellow.repository.PostRepository;
import com.mellow.repository.UserRepository;
import com.mellow.service.exception.DatabaseException;
import com.mellow.service.exception.InvalidInputException;
import com.mellow.service.exception.NoSearchResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public CommentModel getCommentById(Long commentId) {
        return execute(commentRepository1 -> commentRepository1.findOne(commentId),
                "Failed to get comment with id: " + commentId);
    }

    public CommentModel createComment(String content, Long postId, Long userId){
        if(content != null){
            return execute(commentRepository1 -> {
                PostModel post = postRepository.findOne(postId);
                UserModel user = userRepository.findOne(userId);
                if(post != null && user != null){
                    return commentRepository1.save(new CommentModel(content, post).setUser(user));
                }else {
                    throw new NoSearchResultException("Could not find comment with id: " + postId);
                }
            }, "Failed to create comment");
        }else {
            throw new InvalidInputException("Comment content can't be null");
        }
    }

    public CommentModel updateCommentContent(Long commentId, String content){
        if(content != null){
            return execute(commentRepository1 -> {
                CommentModel comment = commentRepository1.findOne(commentId);
                if(comment != null){
                    return commentRepository1.save(comment.setContent(content));
                }else {
                    throw new NoSearchResultException("Could not find comment with id: " + commentId);
                }
            }, "Failed to update comment");
        }else {
            throw new InvalidInputException("Comment content can't be null");
        }
    }

    public CommentModel deleteComment(Long commentId){
        return execute(commentRepository1 -> {
            CommentModel comment = commentRepository1.findOne(commentId);
            if(comment != null){
                commentRepository1.delete(comment);
                return comment;
            }else {
                throw new NoSearchResultException("Could not find comment with id: " + commentId);
            }
        }, "Failed to delete comment");
    }

    private CommentModel execute(Function<CommentRepository, CommentModel> operation, String dbExMsg) {
        try {
            return operation.apply(commentRepository);
        } catch (DataAccessException e) {
            throw new DatabaseException(dbExMsg);
        }
    }
}
