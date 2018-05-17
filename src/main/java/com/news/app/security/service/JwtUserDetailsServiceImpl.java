package com.news.app.security.service;

import com.news.app.entity.User;
import com.news.app.entity.dto.registration.RegistrationRequestDto;
import com.news.app.exception.auth.UserNotFoundException;
import com.news.app.exception.registration.UnconfirmedUserException;
import com.news.app.repository.RegistrationRepository;
import com.news.app.repository.UserRepository;
import com.news.app.security.model.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 07.09.2017 14:24
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("!!!!!!!!!!!!!!!" + username);
        User byUsername = this.userRepository.findByUsername(username);

        return Optional.ofNullable(byUsername).map(JwtUserDetails::new)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
