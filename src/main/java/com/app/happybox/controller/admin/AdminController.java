package com.app.happybox.controller.admin;

import com.app.happybox.entity.board.RecipeBoardDTO;
import com.app.happybox.entity.product.Product;
import com.app.happybox.entity.user.Member;
import com.app.happybox.service.board.RecipeBoardService;
import com.app.happybox.service.order.OrderSubsciptionService;
import com.app.happybox.service.product.ProductService;
import com.app.happybox.service.user.DistributorService;
import com.app.happybox.service.user.MemberService;
import com.app.happybox.service.user.WelfareService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/*")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final RecipeBoardService recipeBoardService;
    private final MemberService memberService;
    private final DistributorService distributorService;
    private final WelfareService welfareService;
    private final ProductService productService;
    private final OrderSubsciptionService orderSubsciptionService;

//    레시피 게시물 목록
    @GetMapping("recipeBoard-list")
    public String getRecipeBoardList(Model model) {
        model.addAttribute("recipeBoards", recipeBoardService.getList(PageRequest.of(0, 5)));
        return "/admin/admin-recipeBoardList";
    }

//    레시피 게시물 조회
    @ResponseBody
    @GetMapping("recipeBoard-detail")
    public String[] getReciepeBoardDetail(@RequestParam("recipeBoardId") Long recipeBoardId) {
        RecipeBoardDTO recipeBoardDTO = recipeBoardService.getRecipeBoardDetailById(recipeBoardId).get();
        String[] reciepeBoard = {};
        return reciepeBoard;
    }

//    회원 목록
    @GetMapping("member-list")
    public String getMemberList(Model model) {
        model.addAttribute("members", memberService.getList(PageRequest.of(0, 5)));
        return "/admin/admin-memberList";
    }

//    회원 조회
    @ResponseBody
    @GetMapping("member-detail")
    public String[] getMemberDetail(@RequestParam("memberId") Long memberId) {
        Member memberInfo = memberService.getDetail(memberId).get();
        String[] member = {
                memberInfo.getMemberName(),
                memberInfo.getUserPhoneNumber(),
                memberInfo.getUserEmail(), String.valueOf(memberInfo.getMemberBirth()),
                String.valueOf(memberInfo.getMemberGender())
        };
        return member;
    }

//    유통회원 목록
    @GetMapping("distributor-list")
    public String getDistributorList(Model model) {
        model.addAttribute("distributors", distributorService.getList(PageRequest.of(0, 5)));
        return "/admin/admin-distributorList";
    }

//    유통회원 조회
    @GetMapping("distributor-detail/{distributorId}")
    public String getDistributorDetail(@PathVariable Long distributorId, Model model) {
        model.addAttribute("distributors", distributorService.getList(PageRequest.of(0, 5)));
        model.addAttribute("distributorId", distributorId);
        model.addAttribute("products", productService.getListByDistributorId(PageRequest.of(0, 5), distributorId));
        return "/admin/admin-distributorDetail";
    }

//    상품 상세보기
    @ResponseBody
    @GetMapping("product-detail")
    public String[] getProductDetail(@RequestParam("productId") Long productId) {
        Product productInfo = productService.getDetailById(productId).get();
        String[] product = {
                productInfo.getProductName(),
                String.valueOf(productInfo.getProductPrice()),
                String.valueOf(productInfo.getProductStock())
        };
        return product;
    }

//    복지관회원 목록
    @GetMapping("welfare-list")
    public String getWelfareList(Model model) {
        model.addAttribute("welfares", welfareService.getList(PageRequest.of(0, 5)));
        return "/admin/admin-welfareList";
    }

//    복지관회원 조회
    @GetMapping("welfare-detail/{welfareId}")
    public String getWelfareDetail(@PathVariable Long welfareId, Model model) {
        String subsciberName = null;
        model.addAttribute("welfare",welfareService.getDetail(welfareId).get());
        model.addAttribute("subscribers", orderSubsciptionService.getListByWelfareId(PageRequest.of(0, 5), welfareId, subsciberName));
        return "/admin/admin-welfareDetail";
    }
}
