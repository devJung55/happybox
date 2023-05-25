package com.app.happybox.controller.mypage;


import com.app.happybox.aspect.annotation.MypageHeaderValues;
import com.app.happybox.domain.FoodCalendarDTO;
import com.app.happybox.domain.InquiryDTO;
import com.app.happybox.domain.OrderSubscriptionDTO;
import com.app.happybox.domain.SubscriptionDTO;
import com.app.happybox.domain.user.MemberDTO;
import com.app.happybox.domain.user.SubscriptionWelFareDTO;
import com.app.happybox.domain.user.WelfareDTO;
import com.app.happybox.domain.welfare.RiderDTO;
import com.app.happybox.entity.file.UserFile;
import com.app.happybox.entity.order.OrderSubscription;
import com.app.happybox.entity.subscript.Rider;
import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.provider.UserDetail;
import com.app.happybox.service.board.RecipeBoardLikeService;
import com.app.happybox.service.board.RecipeBoardService;
import com.app.happybox.service.board.ReviewBoardService;
import com.app.happybox.service.cs.InquiryService;
import com.app.happybox.service.order.MemberOrderProductItemService;
import com.app.happybox.service.order.OrderSubsciptionService;
import com.app.happybox.service.subscript.FoodCalendarService;
import com.app.happybox.service.subscript.RiderService;
import com.app.happybox.service.subscript.SubscriptionLikeService;
import com.app.happybox.service.subscript.SubscriptionService;
import com.app.happybox.service.user.MemberService;
import com.app.happybox.service.user.UserFileService;
import com.app.happybox.service.user.UserService;
import com.app.happybox.service.user.WelfareService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/mypage/*")
@RequiredArgsConstructor
@Slf4j
public class WelfareMyPageController {

    private final RecipeBoardService recipeBoardService;
    private final WelfareService welfareService;
    private final InquiryService inquiryService;
    private final SubscriptionService subscriptionService;
    private final MemberOrderProductItemService memberOrderProductItemService;
    private final UserService userService;
    private final RecipeBoardLikeService recipeBoardLikeService;
    private final SubscriptionLikeService subscriptionLikeService;
    private final MemberService memberService;
    private final OrderSubsciptionService orderSubsciptionService;
    private final UserFileService userFileService;
    private final ReviewBoardService reviewBoardService;
    private final RiderService riderService;
    private final FoodCalendarService foodCalendarService;

    //  복지관 회원 수정 페이지 이동
    @MypageHeaderValues
    @GetMapping("welfare/edit")
    public String goEditForm(@AuthenticationPrincipal UserDetail userDetail, Model model) {
        WelfareDTO welfareDTO = welfareService.getDetail(userDetail.getId());
        model.addAttribute("welfareDTO", welfareDTO);
        return "/mypage/welfare/welfare-editor-form";
    }

    //    복지관 회원 수정 완료
    @PostMapping("welfare/edit")
    public RedirectView edit(WelfareDTO welfareDTO, @AuthenticationPrincipal UserDetail userDetail) {
        Long id = userDetail.getId();
        welfareDTO.setId(id);
        welfareService.updateWelfareInfoById(welfareDTO);
        return new RedirectView("/mypage/welfare/edit");
    }

    //   복지관 구독 수정
    @MypageHeaderValues
    @GetMapping("welfare/subscription/edit")
    public String goSubsedit(@AuthenticationPrincipal UserDetail userDetail, Model model) {
        Long id = userDetail.getId();
        Subscription result = subscriptionService.getId(id);
        SubscriptionWelFareDTO subscriptionWelFareDTO = subscriptionService.getSubscriptionWelfareDTO(result.getId());
        model.addAttribute("subscriptionWelFareDTO", subscriptionWelFareDTO);
        return "/mypage/welfare/welfare-write";

    }

    //    복지관 구독 수정 완료
    @PostMapping("welfare/subscription/edit")
    public RedirectView subsEdit(SubscriptionWelFareDTO subscriptionWelFareDTO) {
        subscriptionService.updateByDTO(subscriptionWelFareDTO);
        return new RedirectView("/mypage/welfare/subscription/edit");
    }

    @MypageHeaderValues
    @GetMapping("welfare/rider/write")
    public String riderWriteForm(@AuthenticationPrincipal UserDetail userDetail, RiderDTO riderDTO, Model model) {
        model.addAttribute("welfareId", userDetail.getId());
        riderDTO.setWelfareId(userDetail.getId());
        model.addAttribute("riderDTO", riderDTO);
        return "/mypage/welfare/rider-register";
    }

    @PostMapping("welfare/rider/write")
    public RedirectView riderWrite(RiderDTO riderDTO, @AuthenticationPrincipal UserDetail userDetail) {
        Long welfareId = userDetail.getId();
        riderDTO.setWelfareId(welfareId);
        riderService.registerRiderByWelfareId(riderDTO);
        return new RedirectView("/mypage/welfare/rider/list");
    }

    @MypageHeaderValues
    @GetMapping("welfare/rider/list")
    public String goRiderListForm(@AuthenticationPrincipal UserDetail userDetail, Model model) {
        Long welfareId = userDetail.getId();
        WelfareDTO welfareDTO = welfareService.getDetail(welfareId);
        model.addAttribute("welfareDTO", welfareDTO);
        return "/mypage/welfare/rider";
    }

    @GetMapping("/welfare/getList")
    @ResponseBody
    public Page<RiderDTO> getList(@PageableDefault(page = 1, size = 7) Pageable pageable, @AuthenticationPrincipal UserDetail userDetail) {
        Long welfareId = userDetail.getId();
        Page<RiderDTO> riderDTOS = riderService.getRiderListByWelfareIdWithPaging(PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize()), welfareId);
        return riderDTOS;
    }

    //    나의 문의내역 목록
    @MypageHeaderValues
    @GetMapping("/welfare/inquiry")
    public String getInquiryList(@AuthenticationPrincipal UserDetail userDetail) {
        return "/mypage/welfare/inquiry";
    }

    //    나의 문의내역 목록
    @ResponseBody
    @GetMapping("/welfare/inquiry/list")
    public Page<InquiryDTO> getInquiryList(@RequestParam(value = "page", defaultValue = "1", required = false) int page, @AuthenticationPrincipal UserDetail userDetail) {
        Page<InquiryDTO> inquiries = inquiryService.getListByMemberId(PageRequest.of(page - 1, 8), userDetail.getId());
        return inquiries;
    }

    //    회원탈퇴
    @MypageHeaderValues
    @GetMapping("/welfare/unregister")
    public String unregister(@AuthenticationPrincipal UserDetail userDetail) {
        return "/mypage/welfare/withdrawal";
    }

    //    회원탈퇴
    @PostMapping("/welfare/unregister")
    public RedirectView unregisterPost(@AuthenticationPrincipal UserDetail userDetail) {
        userService.updateUserStatusByUserId(userDetail.getId());
        return new RedirectView("/login");
    }

    //    캘린더 일정 등록 및 음식 등록 폼 이동
    @MypageHeaderValues
    @GetMapping("/welfare/calendar/write")
    public String goCalendarWriteForm(@AuthenticationPrincipal UserDetail userDetail, FoodCalendarDTO foodCalendarDTO, Model model) {
        Long welfareId = userDetail.getId();
        foodCalendarDTO.setWelfareId(welfareId);
        model.addAttribute("foodCalendareDTO", foodCalendarDTO);
        model.addAttribute("userDetail", userDetail);
        return "/mypage/welfare/food-write";
    }

    //    캘린더 일정 등록 및 음식 등록
    @PostMapping("/welfare/calendar/write")
    @ResponseBody
    public void calendarWrite(@RequestBody FoodCalendarDTO foodCalendarDTO, @AuthenticationPrincipal UserDetail userDetail) {
        foodCalendarDTO.setWelfareId(userDetail.getId());
        Long id = foodCalendarDTO.getId();
        foodCalendarDTO.getFoodList().forEach(foodDTO -> foodDTO.setFoodCalendarId(id));
        foodCalendarService.saveFoodCalendar(foodCalendarDTO);
//        return new RedirectView("/mypage/welfare/edit");
    }


    //    구독자 목록
    @MypageHeaderValues
    @GetMapping("/welfare/subscriber")
    public String getSubscriberList(@AuthenticationPrincipal UserDetail userDetail) {
        return "/mypage/welfare/subscriber";
    }

    //    구독자 목록
    @ResponseBody
    @GetMapping("/welfare/subscriber/list")
    public Page<MemberDTO> getSubscriberList(
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            @AuthenticationPrincipal UserDetail userDetail) {
        String subscriberName = null;
        Page<MemberDTO> subscribers = orderSubsciptionService.getListByWelfareId(PageRequest.of(page - 1, 8), userDetail.getId(), subscriberName);
        List<UserFile> userFiles = userFileService.getList();

        for (int i = 0; i < subscribers.getContent().size(); i++) {
            for (int j = 0; j < userFiles.size(); j++) {
                if (subscribers.getContent().get(i).getId() == userFiles.get(j).getUser().getId()) {
                    MemberDTO memberDTO = subscribers.getContent().get(i);

                    memberDTO.setUserFileDTO(userFileService.userFileToDTO(userFiles.get(j)));
                }
            }
        }
        return subscribers;
    }

    //    구독자 목록
    @ResponseBody
    @GetMapping("/welfare/subscriber/list/searchName")
    public Page<MemberDTO> getSubscriberList(
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            @AuthenticationPrincipal UserDetail userDetail, String subscriberName) {
        Page<MemberDTO> subscribers = orderSubsciptionService.getListByWelfareId(PageRequest.of(page - 1, 8), userDetail.getId(), subscriberName);
        List<UserFile> userFiles = userFileService.getList();

        for (int i = 0; i < subscribers.getContent().size(); i++) {
            for (int j = 0; j < userFiles.size(); j++) {
                if (subscribers.getContent().get(i).getId() == userFiles.get(j).getUser().getId()) {
                    MemberDTO memberDTO = subscribers.getContent().get(i);

                    memberDTO.setUserFileDTO(userFileService.userFileToDTO(userFiles.get(j)));
                }
            }
        }
        return subscribers;
    }
}

