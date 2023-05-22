package com.app.happybox.repository.board;

public interface BoardFileQueryDsl {
    public Long deleteByReviewBoardId(Long id);
    public Long deleteByRecipeBoardId(Long id);
    public Long deleteByDonationBoardId(Long id);
}
