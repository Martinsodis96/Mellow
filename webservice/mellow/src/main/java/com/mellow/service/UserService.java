package com.mellow.service;

import com.mellow.model.User;
import com.mellow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User getById(Long id){
        return userRepository.findOne(id);
    }

    public User updateUser(String username, Long id){
        User user = userRepository.findOne(id);
        user.setUsername(username);
        return userRepository.save(user);
    }

    public User createUser(String username, String firstname, String lastname){
        User user = new User(username, firstname, lastname);
        return userRepository.save(user);
    }

    public User deleteUser(Long id){
        User user = userRepository.findOne(id);
        userRepository.delete(id);
        return user;
    }
}
