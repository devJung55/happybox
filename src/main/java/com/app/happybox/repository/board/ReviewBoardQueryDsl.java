package com.app.happybox.repository.board;

import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.entity.board.ReviewBoardDTO;
import com.app.happybox.entity.user.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

public interface ReviewBoardQueryDsl {
//    목록 페이징(최신순)
    public Slice<ReviewBoard> findAllByIdDescWithPaging_QueryDSL(Pageable pageable);

//    목록 페이징(인기순)
    public Slice<ReviewBoardDTO> findAllByLikeCountDescWithPaging_QueryDSL(Pageable pageable);

//    상세보기
    public Optional<ReviewBoard> findById_QueryDSL(Long id);

//    마이페이지 나의후기 목록
    public Page<ReviewBoard> findAllByMemberIdDescWithPaging_QueryDSL(Pageable pageable, Long memberId);
    
}
