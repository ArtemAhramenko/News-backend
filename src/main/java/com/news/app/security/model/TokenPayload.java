package com.news.app.security.model;

import lombok.NoArgsConstructor;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 07.09.2017 12:41
 */
@NoArgsConstructor
public class TokenPayload {

    private Long userId;
    private long expiration;

    public TokenPayload(final Long userId, final long expiration) {
        this.userId = userId;
        this.expiration = expiration;
    }

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
}
