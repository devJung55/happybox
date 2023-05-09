package com.app.happybox.repository.reply;

import com.app.happybox.entity.reply.RecipeBoardReply;
import com.app.happybox.entity.user.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecipeBoardReplyQueryDsl {
//    마이페이지 레시피 게시물 댓글 조회
    public Page<RecipeBoardReply> findAllByMemberIdDescWithPaging_QueryDSL(Pageable pageable, Long memberId);
}
