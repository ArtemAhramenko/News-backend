package com.news.app.security;


import com.news.app.entity.EnumRoles;
import com.news.app.entity.User;
import org.springframework.stereotype.Component;

/**
 * @author i.katlinsky
 * @since 22.07.2016
 */
@Component
public class AuthUserTransformer {

    public User makeDto(final User user) {
        User authUserDto = new User();

        authUserDto.setId(user.getId());
        authUserDto.setUsername(user.getUsername());
        authUserDto.setRole(EnumRoles.READER);

        return authUserDto;
    }

}
