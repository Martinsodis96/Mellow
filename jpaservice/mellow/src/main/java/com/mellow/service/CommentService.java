package com.mellow.service;

import com.mellow.model.CommentModel;
import com.mellow.model.PostModel;
import com.mellow.model.UserModel;
import com.mellow.repository.CommentRepository;
import com.mellow.repository.PostRepository;
import com.mellow.repository.UserRepository;
import com.mellow.service.exception.DatabaseException;
import com.mellow.service.exception.ForbiddenException;
import com.mellow.service.exception.InvalidInputException;
import com.mellow.service.exception.NoSearchResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository,
                          PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public CommentModel getCommentById(Long commentId) {
        return execute(commentRepository -> commentRepository.findOne(commentId),
                "Failed to get comment with id: " + commentId);
    }

    public CommentModel createComment(String content, Long postId, Long userId) {
        if (Optional.ofNullable(content).isPresent()) {
            return execute(commentRepository -> {
                PostModel post = postRepository.findOne(postId);
                UserModel user = userRepository.findOne(userId);
                if (Optional.ofNullable(post).isPresent() && Optional.ofNullable(user).isPresent()) {
                    return commentRepository.save(new CommentModel(content, post).setUser(user));
                } else {
                    throw new NoSearchResultException(String.format("Could not find post with id '%d' or user with id '%d'", postId, userId));
                }
            }, "Failed to create comment");
        } else {
            throw new InvalidInputException("Comment content can't be null");
        }
    }

    public CommentModel updateCommentContent(Long userId, Long commentId, String content) {
        if (Optional.ofNullable(content).isPresent()) {
            return execute(commentRepository -> {
                CommentModel comment = commentRepository.findOne(commentId);
                if (Optional.ofNullable(comment).isPresent()) {
                    if(!comment.getUser().getId().equals(userId)){
                        throw new ForbiddenException(String.format("Comment with id '%d' do not belong to user with id '%d'", commentId, userId));
                    }
                    return commentRepository.save(comment.setContent(content));
                } else {
                    throw new NoSearchResultException("Could not find comment with id: " + commentId);
                }
            }, "Failed to update comment");
        } else {
            throw new InvalidInputException("Comment content can't be null");
        }
    }

    public CommentModel deleteComment(Long commentId, Long userId) {
        return execute(commentRepository -> {
            CommentModel comment = commentRepository.findOne(commentId);
            if (Optional.ofNullable(comment).isPresent()) {
                if(!comment.getUser().getId().equals(userId)){
                    throw new ForbiddenException(String.format("Comment with id '%d' do not belong to user with id '%d'", commentId, userId));
                }
                commentRepository.delete(comment);
                return comment;
            } else {
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
