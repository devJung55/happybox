package com.app.happybox.repository.board;

import com.app.happybox.entity.board.RecipeBoard;
import com.app.happybox.entity.board.ReviewBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

public interface RecipeBoardQueryDsl {
    //    목록 페이징(최신순)
    public Slice<RecipeBoard> findAllByIdDescWithPaging_QueryDSL(Pageable pageable);

    //    목록 페이징(인기순)
    public Slice<RecipeBoard> findAllByLikeCountDescWithPaging_QueryDSL(Pageable pageable);

    //    마이페이지 목록
    public Page<RecipeBoard> findRecipeBoardListByMemberIdWithPaging_QueryDSL(Pageable pageable, Long memberId);

    //    댓글 목록
    public List<RecipeBoard> findRecipeBoardReplyCountByMemberId_QueryDSL(Long memberId);

    //    레시피 추천순 Top5
    public List<RecipeBoard> findTop5ByLikeCountWithRepresentFileOrderByLikeCount_QueryDSL();

//    마이페이지 게시물건수 조회
    public Long findRecipeBoardCountByIdMemberId_QueryDSL(Long memberId);

//    관리자 레시피 게시글 목록
    public Page<RecipeBoard> findRecipeBoardListDescWithPaging_QueryDSL(Pageable pageable);
}
