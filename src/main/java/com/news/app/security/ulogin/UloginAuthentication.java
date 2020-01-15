package com.news.app.security.ulogin;

import com.news.app.entity.dto.LoginRequestDto;
import com.news.app.service.RegistrationService;
import com.news.app.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UloginAuthentication {

    private final UloginParser uloginParser;

    private final UserService userService;

    private final RegistrationService registrationService;

    public UloginAuthentication(final UloginParser uloginParser,
                                final UserService userService,
                                final RegistrationService registrationService) {
        this.uloginParser = uloginParser;
        this.userService = userService;
        this.registrationService = registrationService;
    }

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
