package com.news.app.service.implemintation;

import com.news.app.entity.User;
import com.news.app.entity.dto.LoginRequestDto;
import com.news.app.security.ulogin.UloginParser;
import com.news.app.service.AuthenticationService;
import com.news.app.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationImpl implements AuthenticationService {

    private final UloginParser uloginParser;

    private final UserService userService;

    public AuthenticationImpl(final UloginParser uloginParser,
                              final UserService userService) {
        this.uloginParser = uloginParser;
        this.userService = userService;
    }

    @Override
    public User addUser(LoginRequestDto loginRequestDto) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (userService.findByUsername(loginRequestDto.getUsername()) == null) {
            return null;
        } else {
            final String password = userService.getByUsername(loginRequestDto.getUsername()).getPassword();
            if (bCryptPasswordEncoder.matches(loginRequestDto.getPassword(), password)) {
                return userService.getByUsername(loginRequestDto.getUsername());
            } else {
                return null;
            }
        }
        // maybe return null in the end and check isPresent(need tested)
    }

    @Override
    public LoginRequestDto addSocialUser(String token) {
        return uloginParser.getUser(token);
    }
}
