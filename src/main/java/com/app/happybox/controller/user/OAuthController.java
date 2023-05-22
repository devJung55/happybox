package com.app.happybox.controller.user;

import com.app.happybox.domain.user.MemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class OAuthController {

    @GetMapping("/")
    public RedirectView oAuthLogin(HttpSession session, RedirectAttributes redirectAttributes){
        log.info(" --------------------- 로그인 후 마지막 컨트롤러  -------------------------- ");
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("member");
        if (memberDTO.getId() == null) {
            redirectAttributes.addFlashAttribute("member", memberDTO);
            return new RedirectView("member/join");
        }
        return new RedirectView("/main/welfare");
    }
}
