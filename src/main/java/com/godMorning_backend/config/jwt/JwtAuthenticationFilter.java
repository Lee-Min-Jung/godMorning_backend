package com.godMorning_backend.config.jwt;
//로그인 진행되면 토큰을 생성해주도록 하는 필터
        //1. username이랑 password 받아서

        //2. 정상인지 로그인 시도를 한다. authenticationManager로 로그인 시도를 하면 PrincipalDatailsService가 호출되고 loadByUsername()함수가 실행된다.

        //3. PrincipalDatails를 세션에 담고 (권한 관리 위해 필요)

        //4. JWT 토큰을 만들어서 응답해주면 됨.

//전체적 로직에 대해서...
    //로그인이 정상으로 되면 서버쪽이 세션 id를 생성해서 클라이언트에게 쿠키 세션 id를 응답해준다
    //이후에는 클라이언트가 인증이 필요한 걸 요청할 때마다 쿠키 세션 id를 항상 들고 서버쪽으로 요청을 하고, 서버는 세션id가 유효한지 판단해서 유효하면 인증이 필요한 페이지로 접근하게 하면 된다.

    //근데 토큰 사용하면 방법이 약간 다르다.
    //로그인이 정상으로 되면 서버는  jwt 토큰을 생성하여 클라이언트에게 jwt토큰을 응답해준다.
    //이후 클라이언트가 인증이 필요한 걸 요청할 때마다 jwt토큰을 가지고 요청하면, 서버는 jwt토큰이 유효한지를 판단해서 유효하면 인증이 필요한 화면을 응답한다.
//근데 궁금한 점: 그럼 토큰은 어디에 저장?
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.godMorning_backend.config.auth.PrincipalDetails;
import com.godMorning_backend.dto.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
@RequiredArgsConstructor
//스프링 시큐리티에 UsernamePasswordAuthenticationFilter가 존재한다.
// /login 주소로 username과 password를 post로 전송하면 UsernamePasswordAuthenticationFilter가 기본으로 동작.
//근데 스프링 시큐리티 설정할 때 formlogin을 비설정하면 UsernamePasswordAuthenticationFilter가 동작하지 않는다.
// 이 필터가 자동 실행되게 해주려면 필터를 다시 스프링 시큐리티 설정에 등록해야 한다.
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;


    // /login 요청하면 로그인 시도를 위해 실행되는 함수, 로그인한 사람이 입력한 정보로 로그인 되는지 확인
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        System.out.println("JwtAuthenticationFilter : 진입");

        // request에 있는 username과 password를 파싱해서 자바 Object로 받기
        ObjectMapper om = new ObjectMapper(); // json 객체를 파싱한다
        LoginRequestDto loginRequestDto = null;
        try {
            loginRequestDto = om.readValue(request.getInputStream(), LoginRequestDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("JwtAuthenticationFilter : "+loginRequestDto);

        // 유저네임패스워드 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword());

        System.out.println("JwtAuthenticationFilter : 토큰생성완료");

        // authenticate() 함수가 호출 되면 인증 프로바이더가 유저 디테일 서비스의
        // loadUserByUsername(토큰의 첫번째 파라메터) 를 호출하고
        // UserDetails를 리턴받아서 토큰의 두번째 파라메터(credential)과
        // UserDetails(DB값)의 getPassword()함수로 비교해서 동일하면
        // Authentication 객체를 만들어서 필터체인으로 리턴해준다.

        // Tip: 인증 프로바이더의 디폴트 서비스는 UserDetailsService 타입
        // Tip: 인증 프로바이더의 디폴트 암호화 방식은 BCryptPasswordEncoder
        // 결론은 인증 프로바이더에게 알려줄 필요가 없음.

        //만든 토큰으로 로그인 시도 해보기, PrincipalDatailsService의 loadUserByUsername 실행됨
        Authentication authentication =
                authenticationManager.authenticate(authenticationToken);

        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("Authentication : "+principalDetailis.getUser().getUsername());
        return authentication;//이게 제대로 되었다는 것은 위에서 생성한 authentication 객체가 세션 영역에 저장되었다는 것. 즉 로그인이 되었다.
        //return을 해주면 세션에 저장이 가능하다.
        //jwt토큰을 만들면서 세션을 사용할 필요가 없는데, 세션이 있어야 권한처리가 쉬워서 사용한다.
    }



    @Override
    //위에 있는 메서드를 통해 로그인이 됨을 확인하면 토큰을 생성해서 헤더에 토큰을 반환해줌
    //attemptAuthentication 실행 후에 인증이 정상적으로 되었을 경우 successfulAuthentication 가 실행됨.
    // 이때 JWT Token 생성해서 response에 담아주기
   protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        System.out.println("인증이 완료되었음");
        PrincipalDetails principalDetailis = (PrincipalDetails) authResult.getPrincipal();

        //hash 암호 방식
        String jwtToken = JWT.create()
                .withSubject(principalDetailis.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
                .withClaim("id", principalDetailis.getUser().getId())
                .withClaim("username", principalDetailis.getUser().getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+jwtToken);
    }
}
