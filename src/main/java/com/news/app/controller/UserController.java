package com.news.app.controller;

import com.news.app.entity.User;
import com.news.app.entity.dto.PageChangesDto;
import com.news.app.entity.dto.UserChangeParamsDto;
import com.news.app.service.UserService;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path="/getuser")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping(path="/getUserByUsername")
    public User getUserByUsername(@RequestBody String username){
        return userService.getByUsername(username);
    }

    @RequestMapping(path="/adduser")
    public void addArticles(User user){
        userService.addUser(user);
    }

    @PostMapping(path="/changeMe")
    public void profileChanges(@RequestBody PageChangesDto pageChangesDto){
        System.out.println(pageChangesDto.getAlias());
        System.out.println(pageChangesDto.getId());
        System.out.println(pageChangesDto.getPassword());
        userService.changeUser(pageChangesDto);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(path="/me/{id}")
    public UserChangeParamsDto showUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping(path="/selectedUser/{id}")
    public UserChangeParamsDto getUserByUsername(@PathVariable Long id){
        return userService.getUserById(id);
    }

}