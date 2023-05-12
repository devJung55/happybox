package com.app.happybox.controller.mypage;

import com.app.happybox.service.board.RecipeBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
public class MypageController {
    private final RecipeBoardService recipeBoardService;

//    나의 게시물 조회(레시피)
    @GetMapping("member/recipe-board")
    public String getUserRecipeBoardList(Pageable pageable, Long memberId, Model model) {
        model.addAttribute("recipeBoards", recipeBoardService.getListByMemberId(PageRequest.of(0, 5), 1L));
        return "/mypage/member/board";
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
