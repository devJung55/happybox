package com.app.happybox.entity.board;

import com.app.happybox.entity.user.Member;
import lombok.Builder;


public class ReviewBoardLikeDTO {
    private Long userId;
    private Long reviewBoardId;

    @Builder
    public ReviewBoardLikeDTO(Long userId, Long reviewBoardId) {
        this.userId = userId;
        this.reviewBoardId = reviewBoardId;
    }
}
