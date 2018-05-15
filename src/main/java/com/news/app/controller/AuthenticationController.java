package com.news.app.controller;

import com.news.app.entity.User;
import com.news.app.entity.dto.LoginRequestDto;
import com.news.app.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/socialAuth")
    private LoginRequestDto addSocailUser(@RequestBody String token) {
        System.out.println(token);
        return authenticationService.addSocialUser(token);
    }

    @PostMapping("/auth")
    private User addUser(@RequestBody LoginRequestDto loginRequestDto) {
        System.out.println(loginRequestDto.getPassword()+" "+loginRequestDto.getUsername());
        return authenticationService.addUser(loginRequestDto);
    }
}
