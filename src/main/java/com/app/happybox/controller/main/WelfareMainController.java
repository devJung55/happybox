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

import javax.servlet.http.HttpServletRequest;
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
    public String goMain(Model model, @AuthenticationPrincipal UserDetail userDetail, HttpServletRequest request) {

        if(userDetail != null) {
            request.getSession().setAttribute("userId", userDetail.getId());
        }

        log.info("user Id : {}", request.getSession().getAttribute("userId"));

        /*log.info(userDetail.toString());*/
        model.addAttribute("recent", subscriptionService.findRecentTop8());
        model.addAttribute("topSale", subscriptionService.findByOrderCount(8L));
        model.addAttribute("donation", donationBoardService.findTop3OrderByDate_QueryDSL());
        model.addAttribute("reviews", reviewBoardService.findTop8Recent());
        return "index/index";
    }

}
