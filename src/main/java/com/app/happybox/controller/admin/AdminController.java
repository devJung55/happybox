package com.app.happybox.controller.admin;

import com.app.happybox.service.board.RecipeBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/*")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final RecipeBoardService recipeBoardService;

//    레시피 게시물 목록
    @GetMapping("recipeBoard-list")
    public String getRecipeBoardList(Model model) {
        model.addAttribute("recipeBoards", recipeBoardService.getList(PageRequest.of(0, 5)));
        return "/admin/admin-recipeBoardList";
    }
}
