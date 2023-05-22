package com.app.happybox.repository.board;

import com.app.happybox.entity.board.RecipeBoard;
import com.app.happybox.entity.board.RecipeBoardLike;
import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecipeBoardLikeQueryDsl {
    public boolean checkMemberLikesRecipeBoard_QueryDSL(Long recipeBoardId, Long memberId);
    public void deleteUserLikeByUserAndRecipeBoard(Long recipeBoardId, Long memberId);

    // 멤버의 좋아요 체크
    public boolean checkMemberLikesRecipeBoard_QueryDSL(Member member, RecipeBoard recipeBoard);
    //    찜목록
    public Page<RecipeBoardLike> findBookmarkListWithMemberIdWithPaging_QueryDSL(Pageable pageable, Long memberId);
}
