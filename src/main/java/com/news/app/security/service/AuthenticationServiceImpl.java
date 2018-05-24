package com.news.app.security.service;

import com.news.app.entity.User;
import com.news.app.entity.dto.LoginRequestDto;
import com.news.app.entity.dto.LoginResponseDto;
import com.news.app.repository.UserRepository;
import com.news.app.security.SecurityHelper;
import com.news.app.security.model.JwtUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class AuthenticationServiceImpl {

    private final UserRepository userRepository;
    private final AuthenticationHelper authenticationHelper;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository, AuthenticationHelper authenticationHelper, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationHelper = authenticationHelper;
        this.authenticationManager = authenticationManager;
    }

    public LoginResponseDto login(final LoginRequestDto loginRequestDto) {
        try {
            String username = Optional.ofNullable(loginRequestDto.getUsername())
                    .orElseThrow(() -> new BadCredentialsException("Username should be passed."));
            String password = Optional.ofNullable(loginRequestDto.getPassword())
                    .orElseThrow(() -> new BadCredentialsException("Password should be passed."));
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username,
                    password);

            // Try to authenticate with this token
            final Authentication authResult = this.authenticationManager.authenticate(authRequest);

            // Set generated JWT token to response header
            if (authResult.isAuthenticated()) {
                JwtUserDetails userDetails = (JwtUserDetails) authResult.getPrincipal();
                User user = userRepository.findOne(userDetails.getId());
                if (Objects.isNull(user)) {
                    throw new RuntimeException("User not exist in system.");
                }

                if(user.isBanned()){
                    throw new RuntimeException("User is banned");
                }

                if(!user.isEnabled()){
                    throw new RuntimeException("Confirm email pls");
                }

                String token = this.authenticationHelper.generateToken(userDetails);
                if (user.getAlias() == null) {
                    user.setAlias("user"+user.getUsername());
                    return new LoginResponseDto(token, user.getAlias());
                } else {
                    return new LoginResponseDto(token, user.getAlias());
                }
            } else {
                throw new RuntimeException("Authentication failed.");
            }

        } catch (BadCredentialsException exception) {
            throw new RuntimeException("Username or password was incorrect. Please try again.", exception);
        }
    }

    @Transactional(readOnly = true)
    public User getMe() {
        Authentication authentication = SecurityHelper.getAuthenticationWithCheck();
        return userRepository.findByUsername(authentication.getName());
    }

    @Transactional(readOnly = true)
    public Long getMyId() {
        Authentication authentication = SecurityHelper.getAuthenticationWithCheck();
        User byUsername = userRepository.findByUsername(authentication.getName());
        return byUsername.getId();
    }
}