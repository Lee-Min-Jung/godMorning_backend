package com.godMorning_backend.controller;
import com.godMorning_backend.dto.MailDto;
import com.godMorning_backend.service.MailService;
import com.godMorning_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import com.godMorning_backend.config.auth.PrincipalDetails;
import com.godMorning_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.godMorning_backend.domain.user.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor

public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;
    private final MailService mailService;

    @PostMapping("join") //회원가입하는 컨트롤러
    public String join(@RequestBody User user) {
        user.setUsername(user.getUsername());
        user.setNickname(user.getNickname());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles("ROLE_USER");
        userRepository.save(user);
        return "회원가입완료";
    }

    @RequestMapping(value="duplicationCheck") //아이디 중복조회 컨트롤러
    public String duplicationcheck(HttpServletRequest request, Model model){
        String nickname = request.getParameter("nickname");

        return userService.duplicationCheck(nickname);
    }

//    @RequestMapping("sendEmail")
//    public String sendEmail(@RequestParam("email") String email){
//        MailDto mailDto = mailService.createMailAndChangePw(email);
//        System.out.println("***************************************");
//        mailService.mailSend(mailDto);
//
//        return "메일을 전송하였습니다.";
//    }


    @PostMapping("withdrawal") //회원탈퇴하는 컨트롤러
    public String withdrawal(@RequestBody User user) {

        //optional 부분 더 좋게 바꿀 수 있을 것 같은데...
        //입력받은 이메일을 통해 DB에서 해당 이메일 회원 조회
        Optional<User> checkUser = Optional.ofNullable(userRepository.findByUsername(user.getUsername()));

        if (checkUser.isPresent()){//입력 이메일이 DB에 있는 경우
            User findUser = userRepository.findByUsername(user.getUsername());
            return userService.withdrawal(user, findUser);
        }
       else{ //입력 이메일이 DB에 없는 경우
           return "이메일을 정확하게 입력해주세요";
        }



    }

}



