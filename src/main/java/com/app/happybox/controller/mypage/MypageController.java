package com.app.happybox.controller.mypage;

import com.app.happybox.domain.*;
import com.app.happybox.domain.user.MemberDTO;
import com.app.happybox.entity.board.RecipeBoardDTO;
import com.app.happybox.entity.user.Address;
import com.app.happybox.entity.user.Member;
import com.app.happybox.service.board.RecipeBoardLikeService;
import com.app.happybox.service.board.RecipeBoardService;
import com.app.happybox.service.cs.InquiryService;
import com.app.happybox.service.order.MemberOrderProductItemService;
import com.app.happybox.service.subscript.SubscriptionLikeService;
import com.app.happybox.service.user.MemberService;
import com.app.happybox.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/mypage/*")
@RequiredArgsConstructor
@Slf4j
public class MypageController {
    private final RecipeBoardService recipeBoardService;
    private final InquiryService inquiryService;
    private final MemberOrderProductItemService memberOrderProductItemService;
    private final UserService userService;
    private final RecipeBoardLikeService recipeBoardLikeService;
    private final SubscriptionLikeService subscriptionLikeService;
    private final MemberService memberService;

    @GetMapping("member/board")
    public void getUserRecipeBoardList() {;}

//    나의 게시물 목록(레시피)
    @ResponseBody
    @GetMapping("member/recipe-board")
    public Page<RecipeBoardDTO> getUserRecipeBoardList(@RequestParam(value = "page", defaultValue = "1", required = false) int page, Long memberId) {
        Page<RecipeBoardDTO> recipeBoards = recipeBoardService.getListByMemberId(PageRequest.of(page - 1, 3), memberId);
        return recipeBoards;
    }

//    나의 문의내역 목록
    @GetMapping("member/inquiry")
    public void getInquiryList() {;}

//    나의 문의내역 목록
    @ResponseBody
    @GetMapping("member/inquiry-list")
    public Page<InquiryDTO> getInquiryList(@RequestParam(value = "page", defaultValue = "1", required = false) int page, Long memberId) {
        Page<InquiryDTO> inquiries = inquiryService.getListByMemberId(PageRequest.of(page - 1, 8), memberId);
        return inquiries;
    }

//    구매 내역 목록
    @GetMapping("member/order")
    public String getOrderList(){
        return "/mypage/member/order-list";
    }

    @ResponseBody
    @GetMapping("member/order-list")
    public Page<MemberOrderProductItemDTO> getOrderList(@RequestParam(value = "page", defaultValue = "1", required = false) int page, Long memberId) {
        Page<MemberOrderProductItemDTO> orderList = memberOrderProductItemService.getListByIdAndSearchDate(PageRequest.of(page - 1, 5), memberId);
        return orderList;
    }

//    레시피 찜 목록
    @GetMapping("member/recipe-bookmark")
    public void getRecipeBookmarkList() {;}

//    레시피 찜 목록
    @ResponseBody
    @GetMapping("member/recipe-bookmark-list")
    public Page<RecipeBoardLikeDTO> getRecipeBookmarkList(@RequestParam(value = "page", defaultValue = "1", required = false) int page, Long memberId) {
        Page<RecipeBoardLikeDTO> bookmarkList = recipeBoardLikeService.getListByMemberId(PageRequest.of(page - 1, 8), memberId);
        return bookmarkList;
    }

//    복지관 찜 목록
    @GetMapping("member/subscription-bookmark")
    public String getSubscriptionBookmarkList() {
        return "/mypage/member/welfare-bookmark";
    }

//    복지관 찜 목록
    @ResponseBody
    @GetMapping("member/subscrition-bookmark-list")
    public Page<SubscriptionLikeDTO> getSubscriptionBookmarkList(@RequestParam(value = "page", defaultValue = "1", required = false) int page, Long memberId) {
        Page<SubscriptionLikeDTO> bookmarkList = subscriptionLikeService.getListSubscriptionBookmarkByMemberId(PageRequest.of(page - 1, 8), memberId);
        return bookmarkList;
    }

//    회원 탈퇴
    @GetMapping("member/unregister")
    public String unregister() {
        return "/mypage/member/withdrawal";
    }

//    회원 탈퇴
    @PostMapping("member/unregister")
    public RedirectView unregister(Long userId) {
        userService.updateUserStatusByUserId(userId);
        return new RedirectView("/main/login");
    }

//    회원정보수정
    @GetMapping("member/edit")
    public String updateMemberInfo(Model model) {
        memberService.getDetail(1L).ifPresent(member ->{
                log.info(member.toString());
                model.addAttribute("member", member);
        });
        return "/mypage/member/member-editor-form";
    }

    @PostMapping("member/edit")
    public RedirectView updateMemberInfo(MemberDTO memberDTO) {
        return new RedirectView("/mypage/member/edit");
    }

//    배송지정보수정
    @GetMapping("member/address-editor")
    public String updateMemberDeliveryAddress(Model model) {
        memberService.getDetail(1L).ifPresent(member -> model.addAttribute("member", member));
        return "/mypage/member/address-editor-form";
    }

//    배송지정보수정
    @PostMapping("member/address-editor")
    public RedirectView updateMemberDeliveryAddress(MemberDTO memberDTO) {
        Member member = memberService.toMemberEntity(memberDTO);
        memberService.updateMemberDeliveryAddressByMemberId(member);
        return new RedirectView("/mypage/member/address-editor");
    }

//    비밀번호 인증
    @GetMapping("member/checkPassword")
    public String checkMemberPassword() {

        return "/mypage/member/member-editor";
    }

//    비밀번호 인증
    @PostMapping("member/checkPassword")
    public RedirectView checkMemberPassword(String password) {

        return new RedirectView("/mypage/member/member-editor-form");
    }
}
