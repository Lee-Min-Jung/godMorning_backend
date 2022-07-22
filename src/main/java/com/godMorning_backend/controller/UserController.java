package com.godMorning_backend.controller;
import com.godMorning_backend.service.UserService;
import org.springframework.security.core.Authentication;
import com.godMorning_backend.config.auth.PrincipalDetails;
import com.godMorning_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.godMorning_backend.domain.user.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;


    @PostMapping("join") //회원가입하는 컨트롤러
    public String join(@RequestBody User user) {
        user.setEmail(user.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles("ROLE_USER");
        userRepository.save(user);
        return "회원가입완료";
    }

    @RequestMapping(value="duplicationCheck") //아이디 중복조회 컨트롤러
    public String duplicationcheck(HttpServletRequest request, Model model){
        String username = request.getParameter("username");

        return userService.duplicationCheck(username);
    }




    @PostMapping("withdrawal") //회원탈퇴하는 컨트롤러
    public String withdrawal(@RequestBody User user) {

        //optional 부분 더 좋게 바꿀 수 있을 것 같은데...
        //입력받은 이메일을 통해 DB에서 해당 이메일 회원 조회
        Optional<User> checkUser = Optional.ofNullable(userRepository.findByEmail(user.getEmail()));

        if (checkUser.isPresent()){//입력 이메일이 DB에 있는 경우
            User findUser = userRepository.findByEmail(user.getEmail());
            return userService.withdrawal(user, findUser);
        }
       else{ //입력 이메일이 DB에 없는 경우
           return "이메일을 정확하게 입력해주세요";
        }



    }

}



