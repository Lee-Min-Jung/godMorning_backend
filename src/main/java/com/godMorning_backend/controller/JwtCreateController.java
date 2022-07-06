package com.godMorning_backend.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.godMorning_backend.config.jwt.JwtProperties;
import com.godMorning_backend.config.oauth.provider.GoogleUser;
import com.godMorning_backend.config.oauth.provider.OAuthUserInfo;
import com.godMorning_backend.domain.user.User;
import com.godMorning_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.Date;

@RestController
@RequiredArgsConstructor
public class JwtCreateController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/oauth/jwt/google")
    public String jwtCreate(@RequestBody Map<String, Object> data) {
        System.out.println("jwtCreate 실행됨");
        System.out.println(data.get("profileObj"));
        OAuthUserInfo googleUser = new GoogleUser((Map<String, Object>)data.get("profileObj"));

        User userEntity = userRepository.findByUsername(googleUser.getProvider()+"_"+googleUser.getProviderId());


        if(userEntity == null){ //만약 회원가입 안 되어 있을 경우 회원가입 진행
            User userRequest = User.builder()
                    .username(googleUser.getProvider()+"_"+googleUser.getProvider())
                    .password(bCryptPasswordEncoder.encode("겟인데어"))
                    .email(googleUser.getEmail())
                    .provider(googleUser.getProvider())
                    .providerId(googleUser.getProviderId())
                    .roles("ROLE_USER")
                    .build();

            userEntity = userRepository.save(userRequest);
        }

        //토큰 만들어서 반환
        String jwtToken = JWT.create()
                .withSubject(userEntity.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+ JwtProperties.EXPIRATION_TIME))
                .withClaim("id", userEntity.getId())
                .withClaim("username", userEntity.getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        return jwtToken;
    }
}
