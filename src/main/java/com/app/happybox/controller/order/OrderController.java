package com.app.happybox.controller.order;

import com.app.happybox.service.order.OrderProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
@Slf4j
public class OrderController {
    private final OrderProductService orderProductService;

    @PostMapping("register")
    @ResponseBody
    public Long register(@RequestParam("cartId") List<Long> cartIds) {
        // 임시 세션 ID 1L
        return orderProductService.saveProductOrder(cartIds, 1L);
    }
}
