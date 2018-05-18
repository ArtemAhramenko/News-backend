package com.news.app.security.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.news.app.entity.EnumRoles;
import com.news.app.entity.User;
import com.news.app.security.exception.InvalidTokenAuthenticationException;
import com.news.app.security.model.JwtUserDetails;
import com.news.app.security.model.TokenPayload;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 07.09.2017 13:03
 */
@Component
@RequiredArgsConstructor
public class AuthenticationHelper {

    private final static Logger logger = LoggerFactory.getLogger(AuthenticationHelper.class);

    public static final String AUTHENTICATION_TOKEN_HEADER = "Authentication";

    private static final Long AUTHENTICATION_TOKEN_EXPIRATION_TIME = 3600L;
    private static final String AUTHENTICATION_TOKEN_GENERATION_SECRET = "changeMe";

    @Resource
    private Environment environment;

    @Autowired
    private ObjectMapper objectMapper;


    public String generateToken(final Long userId, EnumRoles role) {
        try {
            TokenPayload payload = new TokenPayload(
                    userId, Instant.now().getEpochSecond() + this.AUTHENTICATION_TOKEN_EXPIRATION_TIME, role);
            String token = this.objectMapper.writeValueAsString(payload);
            return JwtHelper.encode(token, new MacSigner(AUTHENTICATION_TOKEN_GENERATION_SECRET )).getEncoded();
        } catch (JsonProcessingException e) {
            logger.error(String.format("Error generating token.\n%s", e));
            throw new InternalAuthenticationServiceException("Error generating token.", e);
        }
    }

    public TokenPayload decodeToken(final String token) {
        checkNotNullToken(token);
        Jwt jwt = JwtHelper.decode(token);
        JwtVerification(jwt);
        return getPayload(jwt);
    }

    private void checkNotNullToken(final String token) throws InvalidTokenAuthenticationException {
        if (Objects.isNull(token)) {
            logger.error("Token is null or blank.");
            throw new InvalidTokenAuthenticationException("Token is null or blank.");
        }
    }

    private void JwtVerification(Jwt jwt) throws InvalidTokenAuthenticationException {
        try {
            jwt.verifySignature(new MacSigner(environment.getProperty(AUTHENTICATION_TOKEN_GENERATION_SECRET)));
        } catch (Exception e) {
            logger.error("Token signature verification failure.");
            throw new InvalidTokenAuthenticationException("Token signature verification failure.", e);
        }
    }

    private TokenPayload getPayload(Jwt jwt) throws InvalidTokenAuthenticationException {
        try {
            return this.objectMapper.readValue(jwt.getClaims(), TokenPayload.class);
        } catch (IOException e) {
            throw new InvalidTokenAuthenticationException("Token parsing failed.", e);
        }
    }

}
