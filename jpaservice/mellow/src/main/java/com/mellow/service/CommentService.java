package com.mellow.service;

import com.mellow.model.CommentModel;
import com.mellow.repository.CommentRepository;
import com.mellow.service.exception.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CommentService {

    private CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentModel getCommentById(Long commentId) {
        return execute(commentRepository1 -> commentRepository1.findOne(commentId),
                "Failed to get comment with id: " + commentId);
    }

    private CommentModel execute(Function<CommentRepository, CommentModel> operation, String dbExMsg) {
        try {
            return operation.apply(commentRepository);
        } catch (DataAccessException e) {
            throw new DatabaseException(dbExMsg);
        }
    }
}
