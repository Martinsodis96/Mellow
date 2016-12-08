package com.mellow.service;

import com.mellow.model.User;
import com.mellow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getById(Long id) {
        return userRepository.findOne(id);
    }

    public User updateUser(String username, Long id) {
        User user = userRepository.findOne(id);
        user.setUsername(username);
        return userRepository.save(user);
    }

    public User createUser(User user) {
        if(user.getUsername().length() > 5){
            return userRepository.save(user);
        }else {
            return null;
        }

    }

    public User deleteUser(Long id) {
        User user = userRepository.findOne(id);
        userRepository.delete(id);
        return user;
    }

    public List<User> getAllByPage(int pageNumber, int pageSize, SortType sortType) {
        switch (sortType) {
            case DESC:
                return userRepository.findAll(new PageRequest(pageNumber, pageSize, Sort.Direction.DESC, "id")).getContent();
            default:
                return userRepository.findAll(new PageRequest(pageNumber, pageSize, Sort.Direction.ASC, "id")).getContent();
        }
    }
}
