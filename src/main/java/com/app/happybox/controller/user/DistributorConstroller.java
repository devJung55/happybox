package com.app.happybox.controller.user;

import com.app.happybox.domain.user.DistributorDTO;
import com.app.happybox.service.user.DistributorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/distributor/*"/*, method = RequestMethod.POST*/)
@Slf4j
public class DistributorConstroller {

    private final DistributorService distributorService;
    private final PasswordEncoder passwordEncoder;

//    회원가입 폼
    @GetMapping("join")
    public String goToJoinForm(DistributorDTO distributorDTO){
        return "/member/company-join";
    }

//    회원가입 완료
    @PostMapping("join")
    public RedirectView join(DistributorDTO distributorDTO){
        distributorService.join(distributorDTO,passwordEncoder);
        return new RedirectView("/login");
    }



}
