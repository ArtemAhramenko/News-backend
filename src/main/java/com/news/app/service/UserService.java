package com.news.app.service;

import com.news.app.entity.User;
import com.news.app.entity.dto.PageChangesDto;
import com.news.app.entity.dto.UserChangeParamsDto;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    void addUser(User user);
    User findByUsername(String username);
    User getByUsername(String username);
    void changeUser(PageChangesDto pageChangesDto);
    UserChangeParamsDto getUserById(Long id);
}


