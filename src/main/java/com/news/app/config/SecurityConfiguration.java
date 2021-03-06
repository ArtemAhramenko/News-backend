package com.news.app.config;

import com.news.app.security.handler.RestAccessDeniedHandler;
import com.news.app.security.handler.RestAuthenticationEntryPoint;
import com.news.app.security.service.JwtAuthenticationFilter;
import com.news.app.security.service.JwtAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String[] AUTH_POST_RESPONSE_WHITELIST = new String[]{
            "/registration", "/auth" , "/socialAuth", "/getarticleid/*", "/selectedUser/{id}", "/getsection/{id}", "/getarticleid/{id}"};

    private static final String[] AUTH_GET_RESPONSE_WHITELIST = new String[]{
            "/getarticle", "/getallsections", "/getpopulararticle", "/activate/{code}", "/search/{searchString}"
    };


    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    public SecurityConfiguration(UserDetailsService userDetailsService, JwtAuthenticationProvider jwtAuthenticationProvider) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    @Autowired
    public void configureAuthentication(final AuthenticationManagerBuilder authenticationManagerBuilder)
            throws Exception {
        authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .authenticationProvider(this.jwtAuthenticationProvider)
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(this.passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().

                sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .and()
                .csrf().disable()
                .addFilterAfter(new JwtAuthenticationFilter(authenticationManagerBean()),
                        BasicAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .accessDeniedHandler(new RestAccessDeniedHandler());
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.POST, AUTH_POST_RESPONSE_WHITELIST)
                .antMatchers(HttpMethod.GET, AUTH_GET_RESPONSE_WHITELIST)
                .antMatchers(HttpMethod.OPTIONS, "/**");
    }
}
