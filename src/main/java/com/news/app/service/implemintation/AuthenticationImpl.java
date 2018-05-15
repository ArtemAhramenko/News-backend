package com.news.app.service.implemintation;

import com.news.app.entity.User;
import com.news.app.entity.dto.LoginRequestDto;
import com.news.app.security.ulogin.UloginParser;
import com.news.app.service.AuthenticationService;
import com.news.app.service.RegistrationService;
import com.news.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationImpl implements AuthenticationService {

    @Autowired
    private UloginParser uloginParser;

    @Autowired
    private UserService userService;

    @Autowired
    private RegistrationService registrationService;

    @Override
    public User addUser(LoginRequestDto loginRequestDto) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (userService.findByUsername(loginRequestDto.getUsername()) == null) {
            return null;
        } else {
            if (bCryptPasswordEncoder.matches(loginRequestDto.getPassword(), userService.getByUsername(loginRequestDto.getUsername()).getPassword())) {
                return userService.getByUsername(loginRequestDto.getUsername());
            } else {
                return null;
            }
        }
    }

    @Override
    public LoginRequestDto addSocialUser(String token) {
        return uloginParser.getUser(token);
    }
}
