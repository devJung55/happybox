package com.app.happybox.controller.board;

import com.app.happybox.service.board.ReviewBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user-board/*")
@RequiredArgsConstructor
@Slf4j
public class ReviewBoardController {
    private final ReviewBoardService reviewBoardService;

    @GetMapping("review-board-list")
    public void getList(){;}

}
