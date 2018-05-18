package com.news.app.security.model;

import com.news.app.entity.EnumRoles;
import lombok.NoArgsConstructor;

import java.util.Set;


@NoArgsConstructor
public class TokenPayload {

    private Long userId;
    private long expiration;
    private EnumRoles role;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    public EnumRoles getRole() {
        return role;
    }

    public void setRole(EnumRoles role) {
        this.role = role;
    }

    public TokenPayload(Long userId, long expiration, EnumRoles role) {

        this.userId = userId;
        this.expiration = expiration;
        this.role = role;
    }
}
