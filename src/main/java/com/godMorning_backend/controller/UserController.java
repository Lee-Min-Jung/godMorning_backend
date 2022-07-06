package com.godMorning_backend.controller;
import org.springframework.security.core.Authentication;
import com.godMorning_backend.config.auth.PrincipalDetails;
import com.godMorning_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.godMorning_backend.domain.user.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("home")
    public String home(){

        return "<h1>home</h1>";
    }
    @GetMapping("user") //user 권한 테스트
    public String user(){
        return "user";
    }
    @GetMapping("manager") //manager 권한 테스트
    public String manager(){
        return "manager";
    }

    @GetMapping("admin") //admin 권한 테스트
    public String admin(){
        return "admin";
    }

//    @GetMapping("user")
//    public String user(Authentication authentication) {
//        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
//        System.out.println("principal : "+principal.getUser().getId());
//        System.out.println("principal : "+principal.getUser().getUsername());
//        System.out.println("principal : "+principal.getUser().getPassword());
//        System.out.println("principal : "+principal.getUser().getRoles());
//
//        return "user";
//    }


    @GetMapping("admin/users")
    public List<User> users(){

        return userRepository.findAll();
    }

    @PostMapping("join") //회원가입하는 컨트롤러
    public String join(@RequestBody User user){
        user.setEmail(user.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles("ROLE_USER");
        userRepository.save(user);
        return "회원가입완료";
    }
    //일단 급해서 bye라고 해 놓긴 했는데.. 수정필요
//    @GetMapping("/bye")
//    public SessionUser bye(Model model){
//        SessionUser user = (SessionUser) httpSession.getAttribute("google_user");
//
//
//        if(user != null) {
//            model.addAttribute("name", user.getName());
//        }
//        return user;
//    }

//    @GetMapping("/home")
//    public SessionUser index(Model model){
//        SessionUser user = (SessionUser) httpSession.getAttribute("google_user");
//
//
//        if(user != null) {
//            model.addAttribute("name", user.getName());
//        }
//        return user;
//    }

}