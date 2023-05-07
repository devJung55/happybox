package com.app.happybox.repository.reply;

import com.app.happybox.entity.reply.RecipeBoardReply;
import com.app.happybox.entity.user.Member;

import java.util.List;

public interface RecipeBoardReplyQueryDsl {
//    마이페이지 레시피 게시물 댓글 조회
    public List<RecipeBoardReply> findAllByMemberIdDescWithPaging_QueryDSL(Member member);
}
