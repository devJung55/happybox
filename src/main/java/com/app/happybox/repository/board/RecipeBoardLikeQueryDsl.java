package com.app.happybox.repository.board;

import com.app.happybox.entity.board.RecipeBoardLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecipeBoardLikeQueryDsl {
    //    찜목록
    public Page<RecipeBoardLike> findBookmarkListWithMemberIdWithPaging_QueryDSL(Pageable pageable, Long memberId);
}
