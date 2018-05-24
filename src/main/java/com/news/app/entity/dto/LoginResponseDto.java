package com.news.app.entity.dto;


public class LoginResponseDto {
    private String token;
    private String alias;

    public LoginResponseDto(String token, String alias) {
        this.token = token;
        this.alias = alias;
    }


    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
