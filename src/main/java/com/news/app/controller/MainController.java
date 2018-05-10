package com.news.app.controller;

import com.news.app.entity.User;
import com.news.app.repository.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path="/registration", produces = "application/json")
    public String addNewUser (@RequestBody User user){
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        userRepository.save(newUser);
        System.out.println(user.getUsername() + ' ' + user.getEmail() + ' ' + user.getPassword());
        return JSONObject.quote("Sucsess");
    }
}
