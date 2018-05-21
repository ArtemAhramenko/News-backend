package com.news.app.security.service;

import com.news.app.entity.User;
import com.news.app.repository.UserRepository;
import com.news.app.security.exception.ExpiredTokenAuthenticationException;
import com.news.app.security.exception.InvalidTokenAuthenticationException;
import com.news.app.security.model.JwtAuthenticationToken;
import com.news.app.security.model.JwtUserDetails;
import com.news.app.security.model.TokenPayload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private static final long MILLIS_IN_SECOND = 1000L;

    private final UserRepository userRepository;
    private final AuthenticationHelper authenticationHelper;

    public JwtAuthenticationProvider(UserRepository userRepository, AuthenticationHelper authenticationHelper) {
        this.userRepository = userRepository;
        this.authenticationHelper = authenticationHelper;
    }

    @Override
    public Authentication authenticate(final Authentication authRequest) {
        // Getting string token from authentication request object
        String token = StringUtils.trimToNull((String) authRequest.getCredentials());

        //  Deserialize token
        TokenPayload tokenPayload = authenticationHelper.decodeToken(token);

        // Checking if token already expired and throwing an AuthenticationException in this case
        checkIsExpired(tokenPayload.getExp());

        // Getting user id from token
        Long userEntityId = tokenPayload.getUserId();
        if (Objects.isNull(userEntityId)) {
            throw new InvalidTokenAuthenticationException("Token does not contain a user id.");
        }

        // Getting user from database
        User userInformation = userRepository.findOne(userEntityId);

        if (Objects.isNull(userInformation)) {
            throw new InvalidTokenAuthenticationException("Token does not contain existed user id.");
        }


        // Return authenticated Authentication
        JwtUserDetails userDetails = new JwtUserDetails(userInformation);
        return new JwtAuthenticationToken(userDetails);
    }

    private void checkIsExpired(final Long tokenExpirationTime) {
        if ((System.currentTimeMillis() / MILLIS_IN_SECOND) > tokenExpirationTime) {
            throw new ExpiredTokenAuthenticationException();
        }
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}