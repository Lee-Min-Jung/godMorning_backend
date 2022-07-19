package com.godMorning_backend.config.auth;

import com.godMorning_backend.jwt.JwtAuthenticationFilter;
import com.godMorning_backend.jwt.JwtAuthorizationFilter;


import com.godMorning_backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 되도록 해 준다
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    //@Autowired
   // private CorsConfig corsConfig;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.addFilter(corsConfig.corsFilter())
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//세션을 사용하지 않을 것

                .and()
                .formLogin().disable() //폼태그 만들어 로그인하지 않을 것
                .httpBasic().disable() //프론트에서 헤더 authorization에 id와 pw달고 요청하는 것이 http basic인데 이걸 거부, 그리고 bearer방법을 쓸 것
                                        //bearer 방법은 프론트 헤더 authorization에 토큰을 달고 보내는 것, 토큰으로 jwt를 많이 사용한다

                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository))
                .authorizeRequests()
                .antMatchers("/routine/**").access("hasRole('ROLE_USER')")
                .antMatchers("/scrap/create").access("hasRole('ROLE_USER')")
                .antMatchers("/scrap/delete").access("hasRole('ROLE_USER')")
                .anyRequest().permitAll();






    }
}
