package com.news.app.security.service;

import com.news.app.entity.User;
import com.news.app.entity.dto.LoginRequestDto;
import com.news.app.entity.dto.LoginResponseDto;
import com.news.app.repository.UserRepository;
import com.news.app.security.AuthUserTransformer;
import com.news.app.security.SecurityHelper;
import com.news.app.security.model.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.json.JsonException;
import java.util.Objects;
import java.util.Optional;

/**
 * @author ikatlinsky
 * @since 5/12/17
 */
@Service
@Transactional
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthUserTransformer authUserTransformer;
    private final AuthenticationHelper authenticationHelper;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, AuthUserTransformer authUserTransformer, AuthenticationHelper authenticationHelper, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authUserTransformer = authUserTransformer;
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

                System.out.println("i'm here");
                User user = userRepository.findOne(userDetails.getId());
                if (Objects.isNull(user)) {
                    throw new JsonException("User not exist in system.");
                }
                System.out.println(user.getUsername());
                System.out.println(user.getPassword());
                System.out.println(user.getRole());
                String token = this.authenticationHelper.generateToken(userDetails.getId(), user.getRole());

                return new LoginResponseDto(token);
            } else {
                throw new JsonException("Authentication failed.");
            }

        } catch (BadCredentialsException exception) {
            throw new JsonException("Username or password was incorrect. Please try again.", exception);
        }
    }

    /**
     * Get user info.
     * @return user info.
     */
    @Transactional(readOnly = true)
    public User getMe() {
        Authentication authentication = SecurityHelper.getAuthenticationWithCheck();
        User byUsername = userRepository.findByUsername(authentication.getName());

        return authUserTransformer.makeDto(byUsername);
    }
}
