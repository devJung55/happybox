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
import java.util.Random;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/member/*"/*, method = RequestMethod.POST*/)
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    //    일반회원 회원가입으로 이동
    @GetMapping("join")
    public String JoinForm(MemberDTO memberDTO){
        return "/member/member-join";
    }

    //    일반회원가입 완료
    @PostMapping("join")
    public RedirectView join(MemberDTO memberDTO){
        log.info("들어왔냐?");
        memberService.join(memberDTO,passwordEncoder);
        log.info(memberDTO.toString());
        return new RedirectView("/member/login");
    }

//    logIn폼으로 이동
    @GetMapping("login")
    public String goToLoginForm(){
        return "member/member-login";
    }


    //    아이디 찾기
    @GetMapping("find-id")
    public void goToFindId(){;}

    //    아이디 찾기 실행
    @PostMapping("find-id")
    @ResponseBody
    public String findId(String memberPhone) {
        return memberService.findMemberIdByPhoneNumber(memberPhone).get();
    }

    //    인증번호 보내기
    @PostMapping("sendCode")
    @ResponseBody
    public String sendCode(String memberPhone) {
        Random random = new Random();

        String code = "";
        for (int i = 0; i < 6; i++) {
            String number = Integer.toString(random.nextInt(10));
            code += number;
        }

        memberService.checkSMS(memberPhone, code);
        return code;
    }

}
