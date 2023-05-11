package com.app.happybox.controller.myPage;

import com.app.happybox.service.board.RecipeBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage/*")
@RequiredArgsConstructor
@Slf4j
public class MypageController {
    @Autowired private RecipeBoardService recipeBoardService;

    @GetMapping("user/recipeBoard")
    public void getUserRecipeBoardList(Pageable pageable, Long memberId) {

    }
}
