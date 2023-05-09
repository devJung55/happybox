package com.app.happybox.repository.board;

import com.app.happybox.entity.board.DonationBoard;

import java.util.List;
import java.util.Optional;

public interface DonationBoardQueryDsl {
//    게시물 상세보기
    public Optional<DonationBoard> findById_QueryDSL(Long id);

//    최신순 Top3 조회(메인)
    public List<DonationBoard> findTop3OrderByDate_QueryDSL();
}
