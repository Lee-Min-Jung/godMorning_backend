package com.godMorning_backend.config.auth;

import com.godMorning_backend.domain.user.Role;
import lombok.RequiredArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/test/**","/bye/**","/todo/**","/timezone/**","/newRoutine/**","/routine/**","/wiseSaying/**" ).permitAll()
                   // .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    //.antMatchers( "/routine/**").hasRole("USER")
//
//                    .antMatchers( "/timezone/**").hasRole("USER")
//                    .antMatchers( "/newRoutine/**").hasRole("USER")
                    .anyRequest().authenticated()
                .and()
                    .logout()
                    .logoutSuccessUrl("/bye")
                .and()
                    .oauth2Login()
                    .defaultSuccessUrl("/home", true)
                    .userInfoEndpoint()
                    .userService(customOAuth2UserService);

    }
}
