package com.news.app.entity.dto;

import com.news.app.entity.Articles;

import java.util.List;

public class UserChangeParamsDto {
    private String username;
    private String password;
    private String alias;
    private String profileImg;
    private String email;
    private List<Articles> news;

    public UserChangeParamsDto() {
    }

    public UserChangeParamsDto(String username, String password, String profileImg) {
        this.username = username;
        this.password = password;
        this.profileImg = profileImg;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public List<Articles> getNews() {
        return news;
    }

    public void setNews(List<Articles> news) {
        this.news = news;
    }

    public String getUsername() {

        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
}
