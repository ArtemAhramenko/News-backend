package com.news.app.entity.dto;

public class PageChangesDto {

    private Long id;
    private String password;
    private String alias;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public PageChangesDto() {

    }

    public PageChangesDto(String password, String alias) {

        this.password = password;
        this.alias = alias;
    }
}
