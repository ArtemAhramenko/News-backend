package com.news.app.security.ulogin;


import com.news.app.entity.dto.LoginRequestDto;
import com.news.app.service.RegistrationService;
import com.news.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UloginAuthentication {

    @Autowired
    UserService userService;

    @Autowired
    RegistrationService registrationService;

//    @Autowired
//    RoleService roleService;

//    @Autowired
//    UserTransformer userTransformer;

    LoginRequestDto attemptAuthentication(LoginRequestDto loginRequestDto) {
        System.out.println(loginRequestDto.getUsername() + "!!!!!" + loginRequestDto.getPassword());
        if (userService.findByUsername(loginRequestDto.getUsername()) == null){
            registrationService.register(loginRequestDto);
        }
        return loginRequestDto;
    }
}
