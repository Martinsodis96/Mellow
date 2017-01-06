package com.mellow.service;

import com.mellow.model.LikeModel;
import com.mellow.repository.LikeRepository;
import com.mellow.service.exception.DatabaseException;
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

    public LikeModel getLikeById(Long likeId){
        try {
            return likeRepository.findOne(likeId);
        } catch (DataAccessException e) {
            throw new DatabaseException();
        }
    }
}
