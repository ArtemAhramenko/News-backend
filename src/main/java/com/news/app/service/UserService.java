package com.news.app.service;

import com.news.app.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    void addUser(User user);
    User findByUsername(String username);
    User getByUsername(String username);
}


