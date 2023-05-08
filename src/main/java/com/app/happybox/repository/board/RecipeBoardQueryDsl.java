package com.app.happybox.repository.board;

import com.app.happybox.entity.board.RecipeBoard;
import com.app.happybox.entity.board.RecipeBoardDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecipeBoardQueryDsl {
//    목록 페이징
    public List<RecipeBoardDTO> findRecipeBoardListByMemberIdWithPaging_QueryDSL(Pageable pageable, Long memberId);

//    레시피 추천순 Top5
    public List<RecipeBoard> findTop5ByLikeCountWithRepresentFileOrderByLikeCount_QueryDSL();
}
