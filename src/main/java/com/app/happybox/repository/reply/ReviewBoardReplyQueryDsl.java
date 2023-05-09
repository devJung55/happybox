package com.app.happybox.repository.reply;

import com.app.happybox.entity.reply.ReviewBoardReply;
import com.app.happybox.entity.user.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewBoardReplyQueryDsl {
//    마이페이지 후기 게시물 댓글조회
    public Page<ReviewBoardReply> findAllByMemberIdDescWithPaging_QueryDSL(Pageable pageable, Long memberId);
}
