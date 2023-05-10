package com.app.happybox.controller.user;

import com.app.happybox.type.Gender;
import com.app.happybox.type.Role;
import com.app.happybox.type.UserStatus;
import com.app.happybox.entity.user.MemberDTO;
import com.app.happybox.service.user.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member/*")
@Slf4j
public class memberController {

    private final MemberService memberService;

//    일반회원 회원가입으로 이동
    @GetMapping("join")
    public String goToJoinForm(MemberDTO memberDTO){
        return "/member/member-join";
    }

//    일반회원가입 완료
    @PostMapping("join")
    public RedirectView join(MemberDTO memberDTO){
        memberService.join(memberDTO);
        return new RedirectView("/member/member-login");
    }

}
