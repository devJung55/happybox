package com.app.happybox.repository.board;

import com.app.happybox.entity.board.DonationBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DonationBoardQueryDsl {
//    기부 게시글 목록 페이징 처리
    public Page<DonationBoard> findAllWithPaging(Pageable pageable);

//    최신순 Top3 조회(메인)
    public List<DonationBoard> findTop3OrderByDate_QueryDSL();

//    관리자 기부글 목록
    public Page<DonationBoard> findAllWithPaging_QueryDSL(Pageable pageable);
}
