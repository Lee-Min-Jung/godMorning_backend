package com.godMorning_backend.config.jwt;
//헤더에 있는 토큰 보고 정상 토큰인지 확인하는 필터

//시큐리티는 BasicAuthentication 이라는 필터를 가지고 있다.
//권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터를 무조건 타게 된다.

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.godMorning_backend.config.auth.PrincipalDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.godMorning_backend.domain.user.User;
import com.godMorning_backend.repository.UserRepository;
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);

        this.userRepository = userRepository;

    }

    @Override
    //인증이 필요한 주소요청일 들어오면 이 함수를 실행하게 된다
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("인증이 필요함");
        String header = request.getHeader(JwtProperties.HEADER_STRING);
        System.out.println("header Authorization : " + header);

        //jwt 토큰을 검증 해서 정상적인 사용자인지 확인
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) { //헤더가 없거나 bearer로 시작하지 않아 비정상 상황
            chain.doFilter(request, response);
            return;
        }

        String token = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");//Bearer token 값 중 토큰만 뽑기

        // 토큰 검증 (이게 인증이기 때문에 AuthenticationManager도 필요 없음)
        // 내가 SecurityContext에 집적접근해서 세션을 만들때 자동으로 UserDetailsService에 있는
        // loadByUsername이 호출됨.
        String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token) //위에서 구한 토큰 정상인지 서명
                .getClaim("username").asString(); //서명되면 유저네임 가져오기

        if (username != null) {  //서명이 정상적으로 됨
            User user = userRepository.findByUsername(username); //사용자 중에 위에서 얻은 유저네임 있는지 확인

            // 인증은 토큰 검증시 끝. 인증을 하기 위해서가 아닌 스프링 시큐리티가 수행해주는 권한 처리를 위해
            // 아래와 같이 토큰을 만들어서 Authentication 객체를 강제로 만들고 그걸 세션에 저장!
            PrincipalDetails principalDetails = new PrincipalDetails(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, // 나중에 컨트롤러에서 DI해서
                    // 쓸 때 사용하기 편함.
                    null, // 패스워드는 모르니까 null 처리, 어차피 지금 인증하는게 아니니까!!
                    principalDetails.getAuthorities());

            // 강제로 시큐리티의 세션에 접근하여 값 저장, SecurityContextHolder.getContext()는 시큐리티 세션 공간
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}
