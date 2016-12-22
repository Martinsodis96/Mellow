package com.mellow.service;

import com.mellow.model.UserModel;
import com.mellow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    public enum SortType {ASC, DESC}

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public UserModel getByUsername(String username) {
        return null;
    }

    public UserModel getById(Long id) {
        return userRepository.findOne(id);
    }

    public UserModel updateUser(String username, Long id) {
        UserModel user = userRepository.findOne(id);
        user.setUsername(username);
        return userRepository.save(user);
    }

    public UserModel createUser(UserModel user) {
        if(user.getUsername().length() > 5){
            return userRepository.save(user);
        }else {
            return null;
        }

    }

    public UserModel deleteUser(Long id) {
        UserModel user = userRepository.findOne(id);
        userRepository.delete(id);
        return user;
    }

    public List<UserModel> getAllByPage(int pageNumber, int pageSize, SortType sortType) {
        switch (sortType) {
            case DESC:
             //   return userRepository.findAll(new PageRequest(pageNumber, pageSize, Sort.Direction.DESC, "id")).getContent();
            default:
               // return userRepository.findAll(new PageRequest(pageNumber, pageSize, Sort.Direction.ASC, "id")).getContent();
                return null;
        }
    }
}
