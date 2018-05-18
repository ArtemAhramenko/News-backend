package com.news.app.security.service;

import com.news.app.entity.User;
import com.news.app.entity.dto.registration.RegistrationRequestDto;
import com.news.app.exception.auth.UserNotFoundException;
import com.news.app.exception.registration.UnconfirmedUserException;
import com.news.app.repository.RegistrationRepository;
import com.news.app.repository.UserRepository;
import com.news.app.security.model.JwtUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
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
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final RegistrationRepository registrationDataRepository;

    public JwtUserDetailsServiceImpl(RegistrationRepository registrationDataRepository) {
        this.registrationDataRepository = registrationDataRepository;
    }


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
            System.out.println("------------");
            System.out.println(byUsername.getRole() + " " + byUsername.getUsername());
            return new JwtUserDetails(byUsername);
        }
    }
}
