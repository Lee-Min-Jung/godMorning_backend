package com.godMorning_backend.controller;

import com.godMorning_backend.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final HttpSession httpSession;

    //일단 급해서 bye라고 해 놓긴 했는데.. 수정필요
    @GetMapping("/bye")
    public SessionUser bye(Model model){
        SessionUser user = (SessionUser) httpSession.getAttribute("google_user");


        if(user != null) {
            model.addAttribute("name", user.getName());
        }
        return user;
    }

    @GetMapping("/home")
    public SessionUser index(Model model){
        SessionUser user = (SessionUser) httpSession.getAttribute("google_user");


        if(user != null) {
            model.addAttribute("name", user.getName());
        }
        return user;
    }

}