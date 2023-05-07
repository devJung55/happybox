package com.app.happybox.repository.board;

import com.app.happybox.entity.board.ReviewBoardDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ReviewBoardQueryDsl {
//    목록 페이징(최신순)
    public List<ReviewBoardDTO> findAllByDateDescWithPaging_QueryDSL(Pageable pageable);

//    목록 페이징(인기순)
    public List<ReviewBoardDTO> findAllByLikeCountDescWithPaging_QueryDSL(Pageable pageable);
}
