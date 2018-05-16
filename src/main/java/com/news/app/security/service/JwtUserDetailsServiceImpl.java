package com.news.app.security.service;

import com.news.app.entity.User;
import com.news.app.entity.dto.registration.RegistrationRequestDto;
import com.news.app.exception.auth.UserNotFoundException;
import com.news.app.exception.registration.UnconfirmedUserException;
import com.news.app.repository.RegistrationRepository;
import com.news.app.repository.UserRepository;
import com.news.app.security.model.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 07.09.2017 14:24
 */
@Service
@RequiredArgsConstructor
public class JwtUserDetailsServiceImpl implements UserDetailsService {


    private UserRepository userRepository;
    private RegistrationRepository registrationDataRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User byUsername = this.userRepository.findUserByUsername(username);
        if (byUsername == null) {
            RegistrationRequestDto registrationData = registrationDataRepository.findByUsername(username);
            if (registrationData == null) {
                throw new UserNotFoundException("User not found.");
            } else {
                throw new UnconfirmedUserException();
            }
        } else {
            return new JwtUserDetails(byUsername);
        }
    }
}
