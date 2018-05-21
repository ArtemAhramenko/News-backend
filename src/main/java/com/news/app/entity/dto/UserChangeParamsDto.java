package com.news.app.entity.dto;

public class UserChangeParamsDto {
    private String username;
    private String password;
    private String profileImg;

    public UserChangeParamsDto() {
    }

    public UserChangeParamsDto(String username, String password, String profileImg) {
        this.username = username;
        this.password = password;
        this.profileImg = profileImg;
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

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
}
