package com.mellow.application.jpaservice.service.implementation;

import com.mellow.application.jpaservice.entity.Comment;
import com.mellow.application.jpaservice.entity.Post;
import com.mellow.application.jpaservice.entity.User;
import com.mellow.application.jpaservice.repository.CommentRepository;
import com.mellow.application.jpaservice.repository.PostRepository;
import com.mellow.application.jpaservice.repository.UserRepository;
import com.mellow.application.jpaservice.service.CrudService;
import com.mellow.application.jpaservice.service.exception.DatabaseException;
import com.mellow.application.jpaservice.service.exception.ForbiddenException;
import com.mellow.application.jpaservice.service.exception.InvalidInputException;
import com.mellow.application.jpaservice.service.exception.NoSearchResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class CommentService implements CrudService<Comment> {

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

    @Override
    public List<Comment> getAll() {
        return null;
    }

    @Override
    public Comment getById(Long id) {
        return execute(commentRepository -> commentRepository.findOne(id),
                "Failed to get comment with id: " + id);
    }

    @Override
    public Comment create(Comment comment) {
        if (comment != null && comment.getContent() != null && comment.getUser() != null && comment.getPost() != null) {
            return execute(commentRepository -> {
                Post post = postRepository.findOne(comment.getPost().getId());
                User user = userRepository.findOne(comment.getUser().getId());
                if (Optional.ofNullable(post).isPresent() && Optional.ofNullable(user).isPresent()) {
                    return commentRepository.save(comment);
                } else {
                    throw new NoSearchResultException(String.format("Could not find post with id '%d' or user with id '%d'", comment.getPost().getId(), comment.getUser().getId()));
                }
            }, "Failed to create comment");
        } else {
            throw new InvalidInputException("Comment content, user or post can't be null");
        }
    }

    @Override
    public Comment update(Comment comment) {
        if (comment != null && comment.getUser() != null && comment.getContent() != null) {
            return execute(commentRepository -> {
                Comment existingComment = commentRepository.findOne(comment.getId());
                if (Optional.ofNullable(existingComment).isPresent()) {
                    if (!existingComment.getUser().getId().equals(comment.getUser().getId())) {
                        throw new ForbiddenException(String.format("Comment with id '%d' do not belong to user with id '%d'", comment.getId(), comment.getUser().getId()));
                    }
                    return commentRepository.save(existingComment.setContent(comment.getContent()));
                } else {
                    throw new NoSearchResultException("Could not find comment with id: " + comment.getId());
                }
            }, "Failed to update comment");
        } else {
            throw new InvalidInputException("Comment content can't be null");
        }
    }

    @Override
    public Comment delete(Comment comment) {
        return execute(commentRepository -> {
            if (comment != null && comment.getUser() != null) {
                Comment existingComment = commentRepository.findOne(comment.getId());
                if (Optional.ofNullable(existingComment).isPresent()) {
                    if (!existingComment.getUser().getId().equals(comment.getUser().getId())) {
                        throw new ForbiddenException(String.format("Comment with id '%d' do not belong to user with id '%d'", comment.getId(), comment.getUser().getId()));
                    }
                    commentRepository.delete(existingComment);
                    return existingComment;
                } else {
                    throw new NoSearchResultException("Could not find comment with id: " + comment.getId());
                }
            } else {
                throw new InvalidInputException("Comment or user can't be null");
            }
        }, "Failed to delete comment");
    }

    public List<Comment> getbyPostId(Long postId) {
        try {
            Post postModel = postRepository.findOne(postId);
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

    private Comment execute(Function<CommentRepository, Comment> operation, String dbExMsg) {
        try {
            return operation.apply(commentRepository);
        } catch (DataAccessException e) {
            throw new DatabaseException(dbExMsg);
        }
    }
}
