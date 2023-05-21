package com.app.happybox.controller.mypage;


import com.app.happybox.domain.user.WelfareDTO;
import com.app.happybox.provider.UserDetail;
import com.app.happybox.service.board.RecipeBoardLikeService;
import com.app.happybox.service.board.RecipeBoardService;
import com.app.happybox.service.board.ReviewBoardService;
import com.app.happybox.service.cs.InquiryService;
import com.app.happybox.service.order.MemberOrderProductItemService;
import com.app.happybox.service.order.OrderSubsciptionService;
import com.app.happybox.service.subscript.SubscriptionLikeService;
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
    public RedirectView edit(WelfareDTO welfareDTO){
        welfareService.updateWelfareInfoById(welfareDTO);
        return new RedirectView("/main/welfare");
    }

}
