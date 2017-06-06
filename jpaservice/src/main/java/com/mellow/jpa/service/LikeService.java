package com.mellow.jpa.service;

import com.mellow.jpa.entity.model.LikeModel;
import com.mellow.jpa.repository.LikeRepository;
import com.mellow.jpa.service.exception.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    private LikeRepository likeRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public LikeModel getLikeById(Long likeId) {
        try {
            return likeRepository.findOne(likeId);
        } catch (DataAccessException e) {
            throw new DatabaseException();
        }
    }
}
