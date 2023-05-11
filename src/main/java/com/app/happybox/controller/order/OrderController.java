package com.app.happybox.controller.order;

import com.app.happybox.domain.AddressDTO;
import com.app.happybox.domain.OrderInfoDTO;
import com.app.happybox.service.order.ProductOrderService;
import com.app.happybox.service.order.SubscriptionOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Qualifier("product")
    private final ProductOrderService productOrderService;
    @Qualifier("subscription")
    private final SubscriptionOrderService subscriptionOrderService;

    @PostMapping("product")
    @ResponseBody
    public Long orderProduct(@RequestParam("cartId") List<Long> cartIds, AddressDTO addressDTO, OrderInfoDTO orderInfoDTO) {
        log.info(addressDTO.toString());
        // 임시 세션 ID 1L
        return productOrderService.saveProductOrder(cartIds, 1L, addressDTO, orderInfoDTO);
    }

    @PostMapping("subscription")
    @ResponseBody
    public Integer register(@RequestParam("cartId") List<Long> cartIds, AddressDTO addressDTO, OrderInfoDTO orderInfoDTO) {
        // 임시 세션 ID 1L
        return subscriptionOrderService.saveSubscriptionOrder(cartIds, 1L, addressDTO, orderInfoDTO);
    }
}
