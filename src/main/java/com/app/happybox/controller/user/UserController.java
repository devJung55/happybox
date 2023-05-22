package com.app.happybox.controller.user;

import com.app.happybox.service.user.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;


    //    logIn폼으로 이동
    @GetMapping("/login")
    public String goToLoginForm(){
        return "member/member-login";
    }

    @GetMapping("/logout")
    public String logout(){
        return "/main/welfare";
    }
}
