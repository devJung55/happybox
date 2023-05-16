package com.app.happybox.controller.mypage;

import com.app.happybox.domain.InquiryDTO;
import com.app.happybox.entity.board.RecipeBoardDTO;
import com.app.happybox.entity.customer.Inquiry;
import com.app.happybox.service.board.RecipeBoardService;
import com.app.happybox.service.cs.InquiryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("member/board")
    public void getUserRecipeBoardList() {;}

//    나의 게시물 목록(레시피)
    @ResponseBody
    @GetMapping("member/recipe-board")
    public Page<RecipeBoardDTO> getUserRecipeBoardList(@RequestParam(value = "page", defaultValue = "1", required = false) int page, Long memberId) {
        Page<RecipeBoardDTO> recipeBoards = recipeBoardService.getListByMemberId(PageRequest.of(page - 1, 3), 1L);
        return recipeBoards;
    }

//    나의 문의내역 목록
    @GetMapping("member/inquiry")
    public void getInquiryList() {
        log.info("들어모---------------------------------");
        ;}

//    나의 문의내역 목록
    @ResponseBody
    @GetMapping("member/inquiry-list")
    public Page<InquiryDTO> getInquiryList(@RequestParam(value = "page", defaultValue = "1", required = false) int page, Long memberId) {
        log.info("레스ㅡ=========================================");
        Page<InquiryDTO> inquiries = inquiryService.getListByMemberId(PageRequest.of(page - 1, 5), 1L);
        return inquiries;
    }

//    비밀번호 인증
    @GetMapping("member/checkPassword")
    public String checkMemberPassword() {
        return "/mypage/member/member-editor";
    }

//    비밀번호 인증
    @PostMapping("member/checkPassword")
    public RedirectView checkMemberPassword(String password) {
        return new RedirectView();
    }
}
