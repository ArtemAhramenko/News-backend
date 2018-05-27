package com.news.app.controller;

import com.news.app.entity.User;
import com.news.app.service.UserService;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    @Autowired
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(path="/disableuser")
    public String disableUser(@RequestBody Long id){
        userService.disableUser(id);
        return JSONParser.quote("User with id "+ id.toString() + " is disabled");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(path="/deleteuser")
    public String deleteUser(@RequestBody Long id){
        userService.deleteUser(id);
        return JSONParser.quote("User with id "+ id.toString() + " is deleted");
    }

}
