package com.app.happybox.controller.mypage;


import com.app.happybox.domain.SubscriptionDTO;
import com.app.happybox.domain.user.SubscriptionWelFareDTO;
import com.app.happybox.domain.user.WelfareDTO;
import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.provider.UserDetail;
import com.app.happybox.service.board.RecipeBoardLikeService;
import com.app.happybox.service.board.RecipeBoardService;
import com.app.happybox.service.board.ReviewBoardService;
import com.app.happybox.service.cs.InquiryService;
import com.app.happybox.service.order.MemberOrderProductItemService;
import com.app.happybox.service.order.OrderSubsciptionService;
import com.app.happybox.service.subscript.SubscriptionLikeService;
import com.app.happybox.service.subscript.SubscriptionService;
import com.app.happybox.service.user.MemberService;
import com.app.happybox.service.user.UserFileService;
import com.app.happybox.service.user.UserService;
import com.app.happybox.service.user.WelfareService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

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

//  복지관 회원 수정 페이지 이동
    @GetMapping("welfare/edit")
    public String goEditForm( Model model, @AuthenticationPrincipal UserDetail userDetail){
        WelfareDTO welfareDTO = welfareService.getDetail(userDetail.getId());
        model.addAttribute("welfareDTO",welfareDTO);
        return "/mypage/welfare/welfare-editor-form";
    }

//    복지관 회원 수정 완료
    @PostMapping("welfare/edit")
    public RedirectView edit(WelfareDTO welfareDTO, @AuthenticationPrincipal UserDetail userDetail){
        log.error("===================== 들어왔냐?");
        Long id = userDetail.getId();
        welfareDTO.setId(id);
        welfareService.updateWelfareInfoById(welfareDTO);
        return new RedirectView("/main/welfare");
    }

//    복지관 문의
    @GetMapping("welfare/inquiry")
    public String goInquryForm(Model model, @AuthenticationPrincipal UserDetail userDetail){
        return "/mypage/welfare/inquiry";
    }

//   복지관 구독 수정
    @GetMapping("welfare/subscription/edit")
    public String goSubsedit(Model model, @AuthenticationPrincipal UserDetail userDetail){
        log.error("들어왔냐 여기에????????");
        Long id = userDetail.getId();
        Subscription result = subscriptionService.getId(id);
        SubscriptionWelFareDTO subscriptionWelFareDTO = subscriptionService.getSubscriptionWelfareDTO(result.getId());
        log.error("들엉왔냐?",subscriptionWelFareDTO.toString());
        model.addAttribute("subscriptionWelFareDTO",subscriptionWelFareDTO);
        return "/mypage/welfare/welfare-write";

    }

//    복지관 구독 수정 완료
    @PostMapping("welfare/subscription/edit")
    public RedirectView subsEdit(SubscriptionWelFareDTO subscriptionWelFareDTO, @AuthenticationPrincipal UserDetail userDetail){
        Long id = userDetail.getId();
        log.error("Post에 들어왔어?????");
        subscriptionWelFareDTO.setId(id);
        subscriptionService.updateByDTO(subscriptionWelFareDTO);
        return new RedirectView("/mypage/welfare/subscription/edit");
    }

}
