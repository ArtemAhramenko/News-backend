package com.news.app.service;

import com.news.app.entity.User;
import com.news.app.entity.dto.LoginRequestDto;

public interface AuthenticationService {

    User addUser(LoginRequestDto loginRequestDto);
    LoginRequestDto addSocialUser(String token);

}
