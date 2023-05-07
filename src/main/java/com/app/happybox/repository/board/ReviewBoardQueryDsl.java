package com.app.happybox.repository.board;

import com.app.happybox.entity.board.ReviewBoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ReviewBoardQueryDsl {
//    목록 페이징(최신순)
    public Slice<ReviewBoardDTO> findAllByDateDescWithPaging_QueryDSL(Pageable pageable);

//    목록 페이징(인기순)
    public Slice<ReviewBoardDTO> findAllByLikeCountDescWithPaging_QueryDSL(Pageable pageable);
}
