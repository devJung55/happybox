package com.app.happybox.controller.mypage;

import com.app.happybox.aspect.annotation.MypageHeaderValues;
import com.app.happybox.domain.*;
import com.app.happybox.domain.user.MemberDTO;
import com.app.happybox.entity.board.RecipeBoardDTO;
import com.app.happybox.entity.user.Member;
import com.app.happybox.provider.UserDetail;
import com.app.happybox.service.board.RecipeBoardLikeService;
import com.app.happybox.service.board.RecipeBoardService;
import com.app.happybox.service.cs.InquiryService;
import com.app.happybox.service.order.MemberOrderProductItemService;
import com.app.happybox.service.order.OrderSubsciptionService;
import com.app.happybox.service.subscript.SubscriptionLikeService;
import com.app.happybox.service.user.MemberService;
import com.app.happybox.service.user.UserFileService;
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

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private final OrderSubsciptionService orderSubsciptionService;
    private final UserFileService userFileService;

//    나의 구독 상세보기
    @MypageHeaderValues
    @GetMapping("member/subscribe")
    public String getSubscribeDetail(@AuthenticationPrincipal UserDetail userDetail, Model model) {
        OrderSubscriptionDTO orderSubscriptionDTO = orderSubsciptionService.getSubscriptionDetailByMemberId(userDetail.getId());
        model.addAttribute("subscription", orderSubscriptionDTO);
        model.addAttribute("userFile", userFileService.getDetail(orderSubscriptionDTO.getWelfareId()));
        return "/mypage/member/subscribe";
    }

//    나의 게시물 목록(레시피)
    @MypageHeaderValues
    @GetMapping("member/board")
    public void getUserRecipeBoardList(@AuthenticationPrincipal UserDetail userDetail) {;}

//    나의 게시물 목록(레시피)
    @ResponseBody
    @GetMapping("member/recipe-board")
    public Page<RecipeBoardDTO> getUserRecipeBoardList(@RequestParam(value = "page", defaultValue = "1", required = false) int page, @AuthenticationPrincipal UserDetail userDetail, String setDate, String currentDate) {
        log.info(setDate + "----------------------------");
        log.info(currentDate + "------------------------------");
        Page<RecipeBoardDTO> recipeBoards = recipeBoardService.getListByMemberId(PageRequest.of(page - 1, 3), userDetail.getId());
        return recipeBoards;
    }

//    나의 문의내역 목록
    @MypageHeaderValues
    @GetMapping("member/inquiry")
    public void getInquiryList(@AuthenticationPrincipal UserDetail userDetail) {;}

//    나의 문의내역 목록
    @ResponseBody
    @GetMapping("member/inquiry-list")
    public Page<InquiryDTO> getInquiryList(@RequestParam(value = "page", defaultValue = "1", required = false) int page, @AuthenticationPrincipal UserDetail userDetail) {
        Page<InquiryDTO> inquiries = inquiryService.getListByMemberId(PageRequest.of(page - 1, 8), userDetail.getId());
        return inquiries;
    }

//    구매 내역 목록
    @MypageHeaderValues
    @GetMapping("member/order")
    public String getOrderList(@AuthenticationPrincipal UserDetail userDetail){
        return "/mypage/member/order-list";
    }

    //    구매 내역 목록
    @ResponseBody
    @GetMapping("member/order-list")
    public Page<MemberOrderProductItemDTO> getOrderList(@RequestParam(value = "page", defaultValue = "1", required = false) int page, @AuthenticationPrincipal UserDetail userDetail, String year, String month, String day) {
        SearchDateDTO searchDateDTO = new SearchDateDTO();

        if(year != null) {
            LocalDateTime setDate = LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), 0, 0);
            searchDateDTO.setSetDate(setDate);
        }

        Page<MemberOrderProductItemDTO> orderList = memberOrderProductItemService.getListByIdAndSearchDate(PageRequest.of(page - 1, 5), userDetail.getId(), searchDateDTO);
        return orderList;
    }

//    레시피 찜 목록
    @MypageHeaderValues
    @GetMapping("member/recipe-bookmark")
    public void getRecipeBookmarkList(@AuthenticationPrincipal UserDetail userDetail) {;}

//    레시피 찜 목록
    @ResponseBody
    @GetMapping("member/recipe-bookmark-list")
    public Page<RecipeBoardLikeDTO> getRecipeBookmarkList(@RequestParam(value = "page", defaultValue = "1", required = false) int page, @AuthenticationPrincipal UserDetail userDetail) {
        Page<RecipeBoardLikeDTO> bookmarkList = recipeBoardLikeService.getListByMemberId(PageRequest.of(page - 1, 8), userDetail.getId());
        return bookmarkList;
    }

//    복지관 찜 목록
    @MypageHeaderValues
    @GetMapping("member/subscription-bookmark")
    public String getSubscriptionBookmarkList(@AuthenticationPrincipal UserDetail userDetail) {
        return "/mypage/member/welfare-bookmark";
    }

//    복지관 찜 목록
    @ResponseBody
    @GetMapping("member/subscrition-bookmark-list")
    public Page<SubscriptionLikeDTO> getSubscriptionBookmarkList(@RequestParam(value = "page", defaultValue = "1", required = false) int page, @AuthenticationPrincipal UserDetail userDetail) {
        Page<SubscriptionLikeDTO> bookmarkList = subscriptionLikeService.getListSubscriptionBookmarkByMemberId(PageRequest.of(page - 1, 8), userDetail.getId());
        return bookmarkList;
    }

//    회원 탈퇴
    @MypageHeaderValues
    @GetMapping("member/unregister")
    public String unregister(@AuthenticationPrincipal UserDetail userDetail) {
        return "/mypage/member/withdrawal";
    }

//    회원 탈퇴
    @PostMapping("member/unregister")
    public RedirectView unregisterPost(@AuthenticationPrincipal UserDetail userDetail) {
        userService.updateUserStatusByUserId(userDetail.getId());
        return new RedirectView("/member/login");
    }

//    회원정보수정
    @MypageHeaderValues
    @GetMapping("member/edit")
    public String updateMemberInfo(Model model, @AuthenticationPrincipal UserDetail userDetail) {
        memberService.getDetail(userDetail.getId()).ifPresent(member -> {
            model.addAttribute("member", memberService.toMemberDTO(member));
        });
        return "/mypage/member/member-editor-form";
    }

//    회원정보수정
    @PostMapping("member/edit")
    public RedirectView updateMemberInfo(MemberDTO memberDTO) {
        memberService.updateMemberInfoById(memberDTO);
        return new RedirectView("/mypage/member/edit?update=ok");
    }

//    배송지정보수정
    @MypageHeaderValues
    @GetMapping("member/address-editor")
    public String updateMemberDeliveryAddress(Model model, @AuthenticationPrincipal UserDetail userDetail) {
        memberService.getDetail(userDetail.getId()).ifPresent(member -> model.addAttribute("member", member));
        return "/mypage/member/address-editor-form";
    }

//    배송지정보수정
    @PostMapping("member/address-editor")
    public RedirectView updateMemberDeliveryAddress(MemberDTO memberDTO) {
        Member member = memberService.toMemberEntity(memberDTO);
        memberService.updateMemberDeliveryAddressByMemberId(member);
        return new RedirectView("/mypage/member/address-editor?update=ok");
    }

//    비밀번호 인증
    @MypageHeaderValues
    @GetMapping("member/checkPassword")
    public String checkMemberPassword(@AuthenticationPrincipal UserDetail userDetail) {

        return "/mypage/member/member-editor";
    }

//    비밀번호 인증
    @PostMapping("member/checkPassword")
    public RedirectView checkMemberPassword(String password) {

        return new RedirectView("/mypage/member/member-editor-form");
    }
}
