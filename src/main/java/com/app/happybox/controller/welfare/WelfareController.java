package com.app.happybox.controller.welfare;

import com.app.happybox.domain.FoodCalendarDTO;
import com.app.happybox.domain.FoodCalendarSearchDTO;
import com.app.happybox.domain.SubscriptionCartDTO;
import com.app.happybox.domain.SubscriptionSearchDTO;
import com.app.happybox.domain.user.SubscriptionWelFareDTO;
import com.app.happybox.domain.user.WelfareDTO;
import com.app.happybox.domain.SubscriptionDTO;
import com.app.happybox.entity.subscript.SubscriptionLike;
import com.app.happybox.provider.UserDetail;
import com.app.happybox.service.product.SubscriptionCartService;
import com.app.happybox.service.subscript.FoodCalendarService;
import com.app.happybox.service.subscript.SubscriptionLikeService;
import com.app.happybox.service.subscript.SubscriptionService;
import com.app.happybox.service.user.WelfareService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/welfare")
@Slf4j
public class WelfareController {
    @Qualifier("subscription")
    private final SubscriptionService subscriptionService;
    @Qualifier("subscriptionLike")
    private final SubscriptionLikeService subscriptionLikeService;
    @Qualifier("subscriptionCartService")
    private final SubscriptionCartService subscriptionCartService;
    @Qualifier("foodCalendar")
    private final FoodCalendarService foodCalendarService;
    private final WelfareService welfareService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("list")
    public String goWelfareList(Model model) {
        model.addAttribute("orderCount", subscriptionService.findByOrderCount(4L));
        model.addAttribute("thisMonth", subscriptionService.findAllBetweenDate(LocalDateTime.now()));
        model.addAttribute("likeCount", subscriptionService.findTop3ByLikeCount());

        return "welfare/welfareList";
    }

    @GetMapping("list/search")
    @ResponseBody
    public Page<SubscriptionDTO> getSearchResult(@PageableDefault(page = 1, size = 8) Pageable pageable, SubscriptionSearchDTO subscriptionSearchDTO) {
        return subscriptionService.findBySearch(PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize()), subscriptionSearchDTO);
    }

    @GetMapping("detail/{id}")
    public String goDetail(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetail userDetail) {
        model.addAttribute("subscription", subscriptionService.findByIdWithDetail(id));

        // 좋아요 이미 눌렀는지 검사
        model.addAttribute("isLike", subscriptionLikeService.checkLike(id, userDetail.getId()));
        return "welfare/welfareDetail";
    }

    /* food 가져오는걸로 변경하기 */
    @GetMapping("detail/calendar")
    @ResponseBody
    public List<FoodCalendarDTO> foodCalendars(FoodCalendarSearchDTO searchDTO) {
        return foodCalendarService.getFoodCalendars(searchDTO);
    }

    // 좋아요
    @PostMapping("detail/like/{subscriptionId}")
    @ResponseBody
    public boolean checkLike(@PathVariable Long subscriptionId, @AuthenticationPrincipal UserDetail userDetail) {
        // 임시 회원아이디 1L
        return subscriptionLikeService.checkOutLike(subscriptionId, userDetail.getId());
    }

     // 장바구니
    @PostMapping("cart/add/{subscriptionId}")
    @ResponseBody
    public Long registerCart(@AuthenticationPrincipal UserDetail userDetail, @RequestBody SubscriptionCartDTO subscriptionCartDTO, @PathVariable Long subscriptionId) {
        log.info(subscriptionCartDTO.toString());
        return subscriptionCartService.saveCart(subscriptionCartDTO, userDetail.getId(), subscriptionId);
    }

    //    복지관 회원가입 폼
    @GetMapping("join")
    public String goToJoinForm(WelfareDTO welfareDTO) {
        return "/member/welfare-join";
    }

    //    복지관 회원가입 완료
    @PostMapping("join")
    public RedirectView join(WelfareDTO welfareDTO, SubscriptionWelFareDTO subscriptionWelFareDTO) {
        welfareService.join(welfareDTO, subscriptionWelFareDTO, passwordEncoder);
        log.info("welfareDTO:" + welfareDTO);
        log.info("subscriptionWelFareDTO:" + subscriptionWelFareDTO);
        return new RedirectView("/login");
    }

//    구독했는지 확인
    @GetMapping("check")
    public Boolean checkSubscribe(@RequestParam("welfareId") Long welfareId){
        return subscriptionService.existsByWelfareId(welfareId);
    }

}
