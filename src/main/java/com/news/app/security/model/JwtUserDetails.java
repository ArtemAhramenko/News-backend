package com.news.app.security.model;

import com.news.app.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 07.09.2017 12:30
 */

public class JwtUserDetails implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private boolean isBlocked;
    private Set<GrantedAuthority> grantedAuthorities;

    public JwtUserDetails(User user) {
        this.id =  user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
       // this.isBlocked = user.getIsBlocked();
        this.grantedAuthorities = new HashSet<>();
        this.grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isBlocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public JwtUserDetails() {
    }

    public JwtUserDetails(Long id, String username, String password, boolean isBlocked, Set<GrantedAuthority> grantedAuthorities) {

        this.id = id;
        this.username = username;
        this.password = password;
        this.isBlocked = isBlocked;
        this.grantedAuthorities = grantedAuthorities;
    }
}
