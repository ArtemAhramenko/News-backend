package com.news.app.service;

import com.news.app.entity.User;
<<<<<<< HEAD
import com.news.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users :: add);
        return users;
    }

    public void addUser(User user){
        userRepository.save(user);
    }
=======

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    void addUser(User user);
>>>>>>> master
}
