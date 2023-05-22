package com.app.happybox.repository.board;

import com.app.happybox.entity.board.DonationBoard;
import com.app.happybox.entity.board.ReviewBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

public interface DonationBoardQueryDsl {
    // 현재 시퀀스 가져오기
    public DonationBoard getCurrentSequence_QueryDsl();

//    기부 게시글 목록 페이징 처리(최신순)
    public Slice<DonationBoard> findAllByIdDescWithPaging_QueryDSL(Pageable pageable);

//    기부 게시글 목록 페이징 처리(기부순)
    public Slice<DonationBoard> findAllByPointDescWithPaging_QueryDSL(Pageable pageable);

//    최신순 Top3 조회(메인)
    public List<DonationBoard> findTop3OrderByDate_QueryDSL();

//    관리자 기부글 목록
    public Page<DonationBoard> findAllWithPaging_QueryDSL(Pageable pageable);
}
