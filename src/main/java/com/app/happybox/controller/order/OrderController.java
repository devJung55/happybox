package com.app.happybox.controller.order;

import com.app.happybox.domain.AddressDTO;
import com.app.happybox.domain.OrderInfoDTO;
import com.app.happybox.domain.product.ProductCartDTO;
import com.app.happybox.entity.board.ReviewBoardDTO;
import com.app.happybox.provider.UserDetail;
import com.app.happybox.service.order.ProductOrderService;
import com.app.happybox.service.order.SubscriptionOrderService;
import com.app.happybox.service.product.ProductCartService;
import com.app.happybox.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    private final ProductService productService;
    private final ProductCartService productCartService;

    @GetMapping("product")
    public String goProductOrderForm(@AuthenticationPrincipal UserDetail userDetail, Model model) {
        List<ProductCartDTO> carts = productCartService.findAllByUserId(2L);
        model.addAttribute("carts",carts);
        model.addAttribute("userDetail",userDetail);
        return "market/payment";
    }

    @PostMapping("product")
    @ResponseBody
    public Long orderProduct(@RequestParam("cartId") List<Long> cartIds, AddressDTO addressDTO, OrderInfoDTO orderInfoDTO) {

        return productOrderService.saveProductOrder(cartIds, 1L, addressDTO, orderInfoDTO);
    }


    @PostMapping("subscription")
    @ResponseBody
    public Integer register(@RequestParam("cartId") List<Long> cartIds, AddressDTO addressDTO, OrderInfoDTO orderInfoDTO) {
        // 임시 세션 ID 1L
        return subscriptionOrderService.saveSubscriptionOrder(cartIds, 1L, addressDTO, orderInfoDTO);
    }
}
