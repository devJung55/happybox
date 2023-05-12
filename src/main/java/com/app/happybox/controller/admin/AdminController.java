package com.app.happybox.controller.admin;

import com.app.happybox.entity.user.Distributor;
import com.app.happybox.entity.user.Member;
import com.app.happybox.service.board.RecipeBoardService;
import com.app.happybox.service.user.DistributorService;
import com.app.happybox.service.user.MemberService;
import com.app.happybox.service.user.WelfareService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/*")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final RecipeBoardService recipeBoardService;
    private final MemberService memberService;
    private final DistributorService distributorService;
    private final WelfareService welfareService;

//    레시피 게시물 목록
    @GetMapping("recipeBoard-list")
    public String getRecipeBoardList(Model model) {
        model.addAttribute("recipeBoards", recipeBoardService.getList(PageRequest.of(0, 5)));
        return "/admin/admin-recipeBoardList";
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
        String[] member = {memberInfo.getMemberName(), memberInfo.getUserPhoneNumber(), memberInfo.getUserEmail(), String.valueOf(memberInfo.getMemberBirth()), String.valueOf(memberInfo.getMemberGender())};
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
        log.info("객체--------------------------------" + distributorService.getList( PageRequest.of(0, 5)).getContent());
        model.addAttribute("distributors", distributorService.getList(PageRequest.of(0, 5)));
        model.addAttribute("distributorId", distributorId);
        return "/admin/admin-distributorDetail";
    }

//    복지관회원 목록
    @GetMapping("welfare-list")
    public String getWelfareList(Model model) {
        model.addAttribute("welfares", welfareService.getList(PageRequest.of(0, 5)));
        return "/admin/admin-welfareList";
    }

}
