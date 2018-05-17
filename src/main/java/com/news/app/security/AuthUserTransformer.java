package com.news.app.security;


import com.news.app.entity.User;
import com.news.app.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author i.katlinsky
 * @since 22.07.2016
 */
@Component
@RequiredArgsConstructor
public class AuthUserTransformer {

    public User makeDto(final User user) {
        User authUserDto = new User();

        authUserDto.setId(user.getId());
        authUserDto.setUsername(user.getUsername());
        authUserDto.setRole(UserRole.READER);

        return authUserDto;
    }

}
