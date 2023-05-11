package com.app.happybox.controller.welfare;

import com.app.happybox.entity.product.ProductCartDTO;
import com.app.happybox.entity.product.SubscriptionCart;
import com.app.happybox.entity.product.SubscriptionCartDTO;
import com.app.happybox.service.product.SubscriptionCartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/welfare")
@Slf4j
public class WelfareController {
    private final SubscriptionCartService subscriptionCartService;

    @PostMapping("/add/{subscriptionId}")
    @ResponseBody
    public Long registerCart(@RequestBody SubscriptionCartDTO subscriptionCartDTO, @PathVariable Long subscriptionId) {
        log.info(subscriptionCartDTO.toString());
        // 임시로 회원아이디 1L 넣어둠, 추후 변경
        return subscriptionCartService.saveCart(subscriptionCartDTO, 1L, subscriptionId);
    }
}
