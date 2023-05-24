package com.app.happybox.controller.user;

import com.app.happybox.domain.user.MemberDTO;
import com.app.happybox.provider.UserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class OAuthController {

    @GetMapping("/")
    public RedirectView oAuthLogin(@AuthenticationPrincipal UserDetail userDetail, HttpSession session, RedirectAttributes redirectAttributes){
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("member");
        if (userDetail == null) {
            redirectAttributes.addFlashAttribute("member", memberDTO);
            return new RedirectView("member/join");
        }
        return new RedirectView("/main/welfare");
    }
}
