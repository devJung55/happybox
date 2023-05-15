package com.app.happybox.controller.mypage;

import com.app.happybox.entity.board.RecipeBoardDTO;
import com.app.happybox.service.board.RecipeBoardService;
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

    @GetMapping("member/board")
    public void getUserRecipeBoardList(@RequestParam(value = "page", defaultValue = "1", required = false) int page, Model model) {
        Page<RecipeBoardDTO> list = recipeBoardService.getListByMemberId(PageRequest.of(page - 1, 5), 1L);
        model.addAttribute("recipeBoards", list);
    }

//    나의 게시물 조회(레시피)
    @ResponseBody
    @GetMapping("member/recipe-board/{page}")
    public Page<RecipeBoardDTO> getUserRecipeBoardList(@RequestParam(value = "page", defaultValue = "1", required = false) int page, Long memberId, Model model) {
        log.info(page + "----------------------------");

        Page<RecipeBoardDTO> recipeBoards = recipeBoardService.getListByMemberId(PageRequest.of(page - 1, 5), 1L);
        return recipeBoards;
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
