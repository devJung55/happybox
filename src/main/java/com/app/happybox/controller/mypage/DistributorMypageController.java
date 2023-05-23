package com.app.happybox.controller.mypage;

import com.app.happybox.aspect.annotation.MypageHeaderValues;
import com.app.happybox.domain.InquiryDTO;
import com.app.happybox.domain.MemberOrderProductItemDTO;
import com.app.happybox.domain.SearchDateDTO;
import com.app.happybox.domain.product.ProductDTO;
import com.app.happybox.domain.user.DistributorDTO;
import com.app.happybox.provider.UserDetail;
import com.app.happybox.service.cs.InquiryService;
import com.app.happybox.service.order.MemberOrderProductItemService;
import com.app.happybox.service.product.ProductService;
import com.app.happybox.service.user.DistributorService;
import com.app.happybox.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/mypage/*")
@RequiredArgsConstructor
@Slf4j
public class DistributorMypageController {
    private final DistributorService distributorService;
    private final UserService userService;
    private final InquiryService inquiryService;
    private final ProductService productService;
    private final MemberOrderProductItemService memberOrderProductItemService;

//    회원정보수정
    @MypageHeaderValues
    @GetMapping("distributor/edit")
    public String updateDistributorInfo(@AuthenticationPrincipal UserDetail userDetail, Model model) {
        model.addAttribute("distributorDTO", distributorService.getDetail(userDetail.getId()));
        return "/mypage/business/business-editor-form";
    }

//    회원정보수정
    @PostMapping("distributor/edit")
    public RedirectView updateMemberInfo(DistributorDTO distributorDTO) {
        distributorService.updateDistributorInfoById(distributorDTO);
        return new RedirectView("/mypage/distributor/edit?update=ok");
    }

//    회원탈퇴
    @MypageHeaderValues
    @GetMapping("distributor/unregister")
    public String unregister(@AuthenticationPrincipal UserDetail userDetail) {
        return "/mypage/business/withdrawal";
    }

//    회원탈퇴
    @PostMapping("distributor/unregister")
    public RedirectView unregisterPost(@AuthenticationPrincipal UserDetail userDetail) {
        userService.updateUserStatusByUserId(userDetail.getId());
        return new RedirectView("/login");
    }

    //    나의 문의내역 목록
    @MypageHeaderValues
    @GetMapping("distributor/inquiry")
    public String getInquiryList(@AuthenticationPrincipal UserDetail userDetail) {
        return "/mypage/business/inquiry";
    }

    //    나의 문의내역 목록
    @ResponseBody
    @GetMapping("distributor/inquiry-list")
    public Page<InquiryDTO> getInquiryList(@RequestParam(value = "page", defaultValue = "1", required = false) int page, @AuthenticationPrincipal UserDetail userDetail) {
        Page<InquiryDTO> inquiries = inquiryService.getListByMemberId(PageRequest.of(page - 1, 8), userDetail.getId());
        return inquiries;
    }

//    상품 등록
    @MypageHeaderValues
    @GetMapping("distributor/register")
    public String registerProduct(@AuthenticationPrincipal UserDetail userDetail, Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        return "/mypage/business/product-register";
    }

//    상품 등록
    @PostMapping("distributor/register")
    @ResponseBody
    public void registerProduct(@AuthenticationPrincipal UserDetail userDetail, @RequestBody ProductDTO productDTO) {
        log.info(productDTO.toString());
        productService.saveProduct(userDetail.getId(), productDTO);
    }

//    상품 목록
    @MypageHeaderValues
    @GetMapping("distributor/product")
    public String getProductList(@AuthenticationPrincipal UserDetail userDetail) {
        return "/mypage/business/product-list";
    }

//    상품 목록
    @ResponseBody
    @GetMapping("distributor/product-list")
    public Page<ProductDTO> getProductList(@RequestParam(value = "page", defaultValue = "1", required = false) int page, @AuthenticationPrincipal UserDetail userDetail) {
        Page<ProductDTO> products = productService.getListByDistributorId(PageRequest.of(page - 1, 8), userDetail.getId());
        return products;
    }

//    판매 내역
    @MypageHeaderValues
    @GetMapping("distributor/sale")
    public String getSaleList(@AuthenticationPrincipal UserDetail userDetail) {
        return "/mypage/business/sales-list";
    }

//    판매 내역
    @ResponseBody
    @GetMapping("distributor/sale-list")
    public Page<MemberOrderProductItemDTO> getSaleList(@RequestParam(value = "page", defaultValue = "1", required = false) int page, @AuthenticationPrincipal UserDetail userDetail, String year, String month, String day) {
        SearchDateDTO searchDateDTO = new SearchDateDTO();

        if(year != null) {
            LocalDateTime setDate = LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), 0, 0);
            searchDateDTO.setSetDate(setDate);
        }

        Page<MemberOrderProductItemDTO> sales = memberOrderProductItemService.getSaleListByDistributorIdAndSearchDate(PageRequest.of(page - 1, 5), userDetail.getId(), searchDateDTO);
        return sales;
    }
}
