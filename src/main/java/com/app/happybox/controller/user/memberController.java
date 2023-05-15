package com.app.happybox.controller.user;

import com.app.happybox.domain.user.MemberDTO;
import com.app.happybox.entity.user.Member;
import com.app.happybox.provider.UserDetail;
import com.app.happybox.service.user.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/member/*", method = RequestMethod.POST)
@Slf4j
public class memberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    //    일반회원 회원가입으로 이동
    @GetMapping("join")
    public String goToJoinForm(MemberDTO memberDTO, Model model){
        model.addAttribute("memberDTO",memberDTO);
        return "/member/member-join";
    }

    //    일반회원가입 완료
    @PostMapping("join")
    public RedirectView join(MemberDTO memberDTO){
        log.info("들어왔냐?");
        memberService.join(memberDTO,passwordEncoder);
        log.info(memberDTO.toString());
        return new RedirectView("/main/welfare");
    }

//    logIn폼으로 이동
    @GetMapping("login")
    public String goToLoginForm(){
        return "member/member-login";
    }

//    아이디 중복 검사
    @PostMapping("checkId")
    @ResponseBody
    public String checkId(String userId){
        if (memberService.existsByUserId(userId)){
            return "success";
        }else {
            return "false";
        }
    }

//    휴대폰 중복 검사
    @PostMapping("checkPhoneNumber")
    @ResponseBody
    public String checkPhoneNumber(String userPhoneNumber){
        if (memberService.existsByUserPhoneNumber(userPhoneNumber)){
            return "success";
        }else {
            return "false";
        }
    }

//    이메일 중복검사
    @PostMapping("checkEmail")
    @ResponseBody
    public String checkEmail(String userEmail){
        if (memberService.existsByUserEmail(userEmail)){
            return "success";
        }else {
            return "false";
        }
    }

}
