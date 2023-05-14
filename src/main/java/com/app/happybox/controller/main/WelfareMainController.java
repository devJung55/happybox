package com.app.happybox.controller.main;

import com.app.happybox.entity.subscript.SubscriptionDTO;
import com.app.happybox.service.board.DonationBoardService;
import com.app.happybox.service.board.ReviewBoardService;
import com.app.happybox.service.subscript.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/main/welfare")
@Slf4j
public class WelfareMainController {
    @Qualifier("subscription")
    private final SubscriptionService subscriptionService;

    private final ReviewBoardService reviewBoardService;


    @Qualifier("donationBoard")
    private final DonationBoardService donationBoardService;


    @GetMapping("")
    public String goMain(Model model) {
        model.addAttribute("recent", subscriptionService.findRecentTop8());
        model.addAttribute("topSale", subscriptionService.findByOrderCount(8L));
        model.addAttribute("donation", donationBoardService.findTop3OrderByDate_QueryDSL());
        return "index/index";
    }

}
