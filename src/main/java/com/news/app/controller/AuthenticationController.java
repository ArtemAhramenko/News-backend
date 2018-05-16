package com.news.app.controller;

import com.news.app.entity.User;
import com.news.app.entity.dto.ErrorInfoDto;
import com.news.app.entity.dto.LoginRequestDto;
import com.news.app.exception.auth.AuthenticationFailedException;
import com.news.app.exception.auth.UserNotFoundException;
import com.news.app.exception.registration.UnconfirmedUserException;
import com.news.app.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
