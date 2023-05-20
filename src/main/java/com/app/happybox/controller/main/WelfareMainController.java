package com.app.happybox.controller.main;

import com.app.happybox.domain.user.MemberDTO;
import com.app.happybox.entity.user.Member;
import com.app.happybox.provider.UserDetail;
import com.app.happybox.service.board.DonationBoardService;
import com.app.happybox.service.board.ReviewBoardService;
import com.app.happybox.service.subscript.SubscriptionService;
import com.app.happybox.service.user.MemberService;
import com.app.happybox.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
@Controller
@RequiredArgsConstructor
@RequestMapping("/main/welfare")
@Slf4j
public class WelfareMainController {
    @Qualifier("subscription")
    private final SubscriptionService subscriptionService;

    private final ReviewBoardService reviewBoardService;

    private final UserService userService;

    private final MemberService memberService;

    @Qualifier("donationBoard")
    private final DonationBoardService donationBoardService;

    @GetMapping("")
    public String goMain(Model model, @AuthenticationPrincipal UserDetail userDetail, HttpSession session) {
//        UserDetail이 있다는 것은 일반 로그인으로 진행한 거기 때문에 세션에 값이 안 담겨있으므로
//        userDetail의 id값으로 memberDTO를 찾아서 세션에 담아주기
//        userDetail이 null이라면 oauth로 로그인 했기 때문에 이미 세션에 값이 있음
        if (userDetail!= null) {
            MemberDTO memberDTO =  memberService.getDetail(userDetail.getId());
            session.setAttribute("member", memberDTO);
        }
        /*log.info(userDetail.toString());*/
        model.addAttribute("recent", subscriptionService.findRecentTop8());
        model.addAttribute("topSale", subscriptionService.findByOrderCount(8L));
        model.addAttribute("donation", donationBoardService.findTop3OrderByDate_QueryDSL());
        model.addAttribute("reviews", reviewBoardService.findTop8Recent());
        return "index/index";
    }

}
