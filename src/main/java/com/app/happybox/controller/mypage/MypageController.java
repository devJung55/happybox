package com.app.happybox.controller.mypage;

import com.app.happybox.service.board.RecipeBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage/*")
@RequiredArgsConstructor
@Slf4j
public class MypageController {
    private final RecipeBoardService recipeBoardService;

    @GetMapping("member/recipe-board")
    public String getUserRecipeBoardList(Pageable pageable, Long memberId, Model model) {
        model.addAttribute("recipeBoards", recipeBoardService.getListByMemberId(PageRequest.of(0, 5), 1L));
        return "/mypage/member/board";
    }
}
