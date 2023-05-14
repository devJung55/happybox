package com.app.happybox.service.board;

import com.app.happybox.entity.board.ReviewBoardLike;

public interface ReviewBoardLikeService {
    public void insertHeart(Long reviewBoardId, Long userId);
}
