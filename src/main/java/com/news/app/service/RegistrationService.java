package com.news.app.service;

import com.news.app.entity.dto.LoginRequestDto;
import com.news.app.entity.dto.RegistrationRequestDto;

public interface RegistrationService {
    RegistrationRequestDto register(RegistrationRequestDto registrationRequestDto);
    LoginRequestDto register(LoginRequestDto loginRequestDto);

    boolean activateUser(String code);
}
