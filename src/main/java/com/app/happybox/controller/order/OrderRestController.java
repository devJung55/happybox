package com.app.happybox.controller.order;

import com.app.happybox.domain.AddressDTO;
import com.app.happybox.domain.user.MemberDTO;
import com.app.happybox.entity.user.Address;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.User;
import com.app.happybox.service.order.MemberOrderProductItemService;
import com.app.happybox.service.order.ProductOrderService;
import com.app.happybox.service.user.MemberService;
import com.app.happybox.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/orders")
public class OrderRestController {

    private final ProductOrderService productOrderService;
    private final UserService userService;
    private final MemberService memberService;
    private final MemberOrderProductItemService memberOrderProductItemService;

//    배송지 정보 가져오기
    @GetMapping("address/{userId}")
    public AddressDTO getAddress(@PathVariable(value = "userId")Long userId){
        return userService.findAddressById(userId);
    }

    @GetMapping("purchaser/{id}")
    public MemberDTO getPurchaser(@PathVariable(value = "id")Long Id){
        return memberService.findDeliveryInfoById(Id);
    }

    @GetMapping("member/{id}")
    public MemberDTO getMember(@PathVariable(value = "id")Long id){
        return memberService.findDeliveryInfoById(id);
    }

    @PostMapping("add/cart")
    public void addCart(){

    }

}
