package com.news.app.controller;

import com.news.app.entity.User;
import com.news.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path="/getuser")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @RequestMapping(path="/adduser")
    public void addArticles(User user){
        userService.addUser(user);
    }

    @GetMapping(path="/me")
    public String showUser() {
        return "auth";
    }

//    @GetMapping(path="/me")
//    public List<UserRole> showRoles() {
//        return userRoleService.getRoleNames();
//    }


}