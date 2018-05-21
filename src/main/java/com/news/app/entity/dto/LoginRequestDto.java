package com.news.app.entity.dto;

import org.springframework.stereotype.Component;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 09.09.2017 18:02
 */

@Component
public class LoginRequestDto {
    private String username;
    private String password;

    public LoginRequestDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}