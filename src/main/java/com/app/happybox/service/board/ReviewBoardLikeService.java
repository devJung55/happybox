package com.app.happybox.service.board;

import com.app.happybox.entity.board.ReviewBoardLike;

public interface ReviewBoardLikeService {
    public boolean checkOutLike(Long reviewBoardId, Long userId);

    public boolean checkLike(Long reviewBoardId, Long memberId);
}
