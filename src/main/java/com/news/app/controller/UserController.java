package com.news.app.controller;

import com.news.app.entity.Articles;
import com.news.app.entity.User;
import com.news.app.repository.UserRepository;
import com.news.app.service.ArticlesService;
import com.news.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(path="/getuser")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @RequestMapping(path="/adduser")
    public void addArticles(User user){
        userService.addUser(user);
    }
}
