package com.news.app.security.ulogin;

import com.news.app.entity.User;
import com.news.app.entity.dto.LoginRequestDto;
import com.news.app.entity.dto.UserRegisterDto;
import com.news.app.service.RegistrationService;
import com.news.app.service.RoleService;
import com.news.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UloginAuthentication {

    @Autowired
    private UloginParser uloginParser;

    @Autowired
    UserService userService;

    @Autowired
    RegistrationService registrationService;

    @Autowired
    RoleService roleService;

    public LoginRequestDto attemptAuthentication(String token) {

        LoginRequestDto user = uloginParser.getUser(token);

        LoginRequestDto loginRequestDto = new LoginRequestDto(user.getUsername(), user.getPassword());
        System.out.println(user.getUsername() + "!!!!!" + user.getPassword());
        if (userService.findByUsername(loginRequestDto.getUsername()) == null){
            registrationService.register(loginRequestDto);
        }
        return loginRequestDto;
    }
}
