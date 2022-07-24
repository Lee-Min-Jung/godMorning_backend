package com.godMorning_backend.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.godMorning_backend.jwt.JwtProperties;
import com.godMorning_backend.config.oauth.provider.GoogleUser;
import com.godMorning_backend.config.oauth.provider.OAuthUserInfo;
import com.godMorning_backend.domain.user.User;
import com.godMorning_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;

@RestController
@RequiredArgsConstructor
public class JwtController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    //프엔으로부터 구글 뭘 받아서 구글 사용자 토큰 생성하는 컨트롤러
    @PostMapping("/oauth/jwt/google")
    //프론트엔드가 data를 body에 보냄. 근데 body는 String: 객체 이런식으로 되어 있는듯, data는 구글 로그인 한 후 받은 토큰인가..?
    public String jwtCreate(@RequestBody Map<String, Object> data) {
        System.out.println("jwtCreate 실행됨");
        System.out.println(data.get("profileObj")); //profileObj: OO 이런 형태로 되어 있나?
        //프엔이 보낸 data를 이용해서 googleUser 도메인에 값 각각 찾아서 집어넣도록 함
        OAuthUserInfo googleUser = new GoogleUser((Map<String, Object>)data.get("profileObj"));//프엔이 보낸 data에서 profileObj를 찾아서 GoogleUser에 넘겨주기

        //위의 코드를 통해 google로그인 된 사용자 정보가 GoogleUser에 담겼을 것.
        //현재 user DB에 위에 담긴 google사용자가 있는지 없는지 확인
        User userEntity = userRepository.findByUsername(googleUser.getProvider()+"_"+googleUser.getProviderId());


        if(userEntity == null){ //만약 회원가입 안 되어 있을 경우 회원가입 진행
            User userRequest = User.builder()
                    .username(googleUser.getProvider()+"_"+googleUser.getProviderId())
                    .password(bCryptPasswordEncoder.encode("겟인데어"))
                    .nickname(googleUser.getName())
                    .provider(googleUser.getProvider())
                    .providerId(googleUser.getProviderId())
                    .roles("ROLE_USER")
                    .build();

            userEntity = userRepository.save(userRequest);
        }

        //토큰 만들어서 반환
        String jwtToken = JWT.create()
                .withSubject(userEntity.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+ JwtProperties.ACCESS_EXPIRATION_TIME))
                .withClaim("id", userEntity.getId())
                .withClaim("username", userEntity.getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        return jwtToken;
    }

    //프엔으로부터 refresh token 받아서 유효성 검사
//    @PostMapping("/refresh")
//    public String validateRefreshToken(@RequestBody HashMap<String, String> bodyJson){
//        System.out.println("refresh controller 실행");
//        Map<String, String> map =
//    }
}
