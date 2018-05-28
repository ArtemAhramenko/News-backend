package com.news.app.controller;

import com.news.app.entity.dto.ErrorInfoDto;
import com.news.app.entity.dto.LoginRequestDto;
import com.news.app.entity.dto.LoginResponseDto;
import com.news.app.exception.auth.AuthenticationFailedException;
import com.news.app.exception.auth.UserNotFoundException;
import com.news.app.exception.registration.UnconfirmedUserException;


import com.news.app.security.service.AuthenticationServiceImpl;

import com.news.app.security.ulogin.UloginAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    @Autowired
    AuthenticationServiceImpl authenticationService;

    @Autowired
    UloginAuthentication uloginAuthentication;

    @PostMapping("/socialAuth")
    private LoginResponseDto addSocailUser(@RequestBody String token) {
        LoginRequestDto loginRequestDto = uloginAuthentication.attemptAuthentication(token);
        System.out.println(loginRequestDto.getUsername());
        System.out.println(loginRequestDto.getPassword());
        return authenticationService.login(loginRequestDto);
    }

    @PostMapping("/auth")
    private LoginResponseDto addUser(@RequestBody LoginRequestDto loginRequestDto) {
        System.out.println(loginRequestDto.getPassword() + " " + loginRequestDto.getUsername());
        return authenticationService.login(loginRequestDto);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(BadCredentialsException.class)
    public ErrorInfoDto usernameOrPasswordNotPassed(HttpServletRequest request, Exception exception) {
        return new ErrorInfoDto(request.getRequestURL().toString(), exception);
    }

    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(AuthenticationFailedException.class)
    public ErrorInfoDto authenticationFailed(HttpServletRequest request, Exception exception) {
        return new ErrorInfoDto(request.getRequestURL().toString(), exception);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorInfoDto userNotFound(HttpServletRequest request, Exception exception) {
        return new ErrorInfoDto(request.getRequestURL().toString(), exception);
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnconfirmedUserException.class)
    public ErrorInfoDto userUnconfirmed(HttpServletRequest request, Exception exception) {
        return new ErrorInfoDto(request.getRequestURL().toString(), exception);
    }
}
