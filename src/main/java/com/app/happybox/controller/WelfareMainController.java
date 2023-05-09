package com.app.happybox.controller;

import com.app.happybox.entity.subscript.SubscriptionDTO;
import com.app.happybox.service.subscript.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/main")
@Slf4j
public class WelfareMainController {
    @Qualifier("subscription")
    private final SubscriptionService subscriptionService;

    @GetMapping("")
    public String goMain(Model model) {
        model.addAttribute("recent", subscriptionService.findRecentTop8());
        return "index/index";
    }

}
