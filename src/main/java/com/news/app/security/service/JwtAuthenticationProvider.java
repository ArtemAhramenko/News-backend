package com.news.app.security.service;

import com.news.app.entity.User;
import com.news.app.exception.auth.JwtAccountLockedException;
import com.news.app.repository.UserRepository;
import com.news.app.security.exception.ExpiredTokenAuthenticationException;
import com.news.app.security.exception.InvalidTokenAuthenticationException;
import com.news.app.security.model.JwtAuthenticationToken;
import com.news.app.security.model.JwtUserDetails;
import com.news.app.security.model.TokenPayload;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 07.09.2017 17:58
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private static final long MILLS_IN_SECOND = 1000L;

    private UserRepository userRepository;
    private AuthenticationHelper authenticationHelper;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        TokenPayload tokenPayload = getAndDeserializeToken(authentication);
        validateTokenPayload(tokenPayload);
        User user = this.userRepository.findOne(tokenPayload.getUserId());
        checkNotNull(user, "Token does not contain a user id.");
        return new JwtAuthenticationToken(checkAccountLocked(new JwtUserDetails(user)));
    }

    private TokenPayload getAndDeserializeToken(Authentication authentication) {
        String token = StringUtils.trimToNull((String) authentication.getCredentials());
        return authenticationHelper.decodeToken(token);
    }

    private void validateTokenPayload(TokenPayload tokenPayload) {
        checkIsExpired(tokenPayload.getExpiration());
        checkNotNull(tokenPayload.getUserId(), "Token does not contain a user id.");
    }

    private JwtUserDetails checkAccountLocked(JwtUserDetails userDetails) {
        if (!userDetails.isAccountNonLocked()) {
            throw new JwtAccountLockedException("This account are blocked.");
        }
        return userDetails;
    }

    private void checkNotNull(Object value, String badCauseMessage) {
        if (Objects.isNull(value)) {
            throw new InvalidTokenAuthenticationException(badCauseMessage);
        }
    }

    private void checkIsExpired(final Long tokenExpirationTime) {
        if (System.currentTimeMillis() / MILLS_IN_SECOND > tokenExpirationTime) {
            throw new ExpiredTokenAuthenticationException();
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
