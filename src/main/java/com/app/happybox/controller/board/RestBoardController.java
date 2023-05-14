package com.app.happybox.controller.board;

import com.app.happybox.service.board.DonationBoardService;
import com.app.happybox.service.board.RecipeBoardService;
import com.app.happybox.service.board.ReviewBoardLikeService;
import com.app.happybox.service.board.ReviewBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-board/*")
@RequiredArgsConstructor
@Slf4j
public class RestBoardController {

    @Qualifier
    private final ReviewBoardService reviewBoardService;
    @Qualifier
    private final ReviewBoardLikeService reviewBoardLikeService;
    @Qualifier
    private final RecipeBoardService recipeBoardService;
    @Qualifier
    private final DonationBoardService donationBoardService;

    @GetMapping("heart-insert/{reviewBoardId}")
    public void insertHeart(@PathVariable Long userId, @PathVariable Long reviewBoardId){
        reviewBoardLikeService.insertHeart(userId, reviewBoardId);
    }
}
