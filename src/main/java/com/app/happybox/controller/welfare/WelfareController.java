package com.app.happybox.controller.welfare;

import com.app.happybox.domain.FoodCalendarDTO;
import com.app.happybox.domain.FoodCalendarSearchDTO;
import com.app.happybox.domain.SubscriptionCartDTO;
import com.app.happybox.domain.SubscriptionSearchDTO;
import com.app.happybox.domain.user.SubscriptionWelFareDTO;
import com.app.happybox.domain.user.WelfareDTO;
import com.app.happybox.domain.SubscriptionDTO;
import com.app.happybox.entity.subscript.Subscription;
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
import org.springframework.data.domain.Slice;
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

    @GetMapping("/chat/list/search")
    @ResponseBody
    public Slice<WelfareDTO> getSearchResult(@PageableDefault(page = 1, size = 5) Pageable pageable, String welfareName) {
        return welfareService.getListBySearch(PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize()), welfareName);
    }
    @GetMapping("detail/{id}")
    public String goDetail(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetail userDetail) {
        model.addAttribute("subscription", subscriptionService.findByIdWithDetail(id));
        model.addAttribute("userId",userDetail.getId());

        // 좋아요 이미 눌렀는지 검사
        boolean checkLike = userDetail != null && subscriptionLikeService.checkLike(id, userDetail.getId());
        model.addAttribute("isLike", checkLike);
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
    public Integer checkLike(@PathVariable Long subscriptionId, @AuthenticationPrincipal UserDetail userDetail) {

        int result = 0;
        // 비로그인시
        if(userDetail == null) {
            return -1;
        }
        boolean check = subscriptionLikeService.checkOutLike(subscriptionId, userDetail.getId());
        // 좋아요
        result = check ? 1 : 0;
        return result;
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

//    cart안에 있는지 확인
    @GetMapping("cart/check")
    public Integer checkCart(@RequestParam Long subscriptionId){
        log.error("여기에 AJAX 쏴졌냐?>");
        Integer result = subscriptionCartService.subscriptionCartCheck(subscriptionId);
        log.error("값이 나왔냐",result.toString());
        return result;

    }

//    user id로 cart 삭제
    @GetMapping("cart/delete")
    public Boolean deleteCart(@RequestParam Long id){
        log.error("=============삭제하기 전======================");
       subscriptionCartService.deleteCart(id);
        log.error("=============삭제하기 후======================");
       if(subscriptionCartService.findAllByUserId(id).isEmpty()){
           log.error("============= 카트안에 비어있을때 ======================");
           return true;
       }
       else {
           log.error("============= 카트안에 있을때 ======================");
           return false;
       }
    }
}
