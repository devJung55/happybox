package com.app.happybox.controller.mypage;

import com.app.happybox.aspect.annotation.MypageHeaderValues;
import com.app.happybox.domain.InquiryDTO;
import com.app.happybox.domain.user.DistributorDTO;
import com.app.happybox.domain.user.MemberDTO;
import com.app.happybox.provider.UserDetail;
import com.app.happybox.service.cs.InquiryService;
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

@Controller
@RequestMapping("/mypage/*")
@RequiredArgsConstructor
@Slf4j
public class DistributorMypageController {
    private final DistributorService distributorService;
    private final UserService userService;
    private final InquiryService inquiryService;

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
}
