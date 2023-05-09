package com.app.happybox.repository.board;

import com.app.happybox.entity.board.RecipeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecipeBoardQueryDsl {
//    마이페이지 목록
    public Page<RecipeBoard> findRecipeBoardListByMemberIdWithPaging_QueryDSL(Pageable pageable, Long memberId);

//    댓글 수
    public List<RecipeBoard> findRecipeBoardReplyCountByMemberId_QueryDSL(Long memberId);

//    레시피 추천순 Top5
    public List<RecipeBoard> findTop5ByLikeCountWithRepresentFileOrderByLikeCount_QueryDSL();

}
