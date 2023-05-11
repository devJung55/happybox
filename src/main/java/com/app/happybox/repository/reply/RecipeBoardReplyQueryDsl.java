package com.app.happybox.repository.reply;

import com.app.happybox.entity.reply.RecipeBoardReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecipeBoardReplyQueryDsl {
//    마이페이지 레시피 게시물 댓글 조회
    public Page<RecipeBoardReply> findAllByMemberIdDescWithPaging_QueryDSL(Pageable pageable, Long memberId);
}
