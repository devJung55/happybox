package com.app.happybox.repository.reply;

import com.app.happybox.entity.reply.Reply;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.User;

public interface ReplyLikeQueryDsl {
//    상품 댓글 좋아요 눌렀는지 확인
    public boolean checkUserLikesReply_QueryDSL(Long id, Long userId);

//    회원의 해당 댓글의 좋아요 취소(삭제)
    public void deleteUserLikeByUserAndReply(Long id, Long userId);
}
