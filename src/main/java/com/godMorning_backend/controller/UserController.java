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

    @GetMapping("/")
    public SessionUser index(Model model){
        SessionUser user = (SessionUser) httpSession.getAttribute("user");


        if(user != null) {
            model.addAttribute("userName", user.getName());
        }
        return user;
    }
}