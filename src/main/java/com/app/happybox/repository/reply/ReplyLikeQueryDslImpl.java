package com.app.happybox.repository.reply;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.app.happybox.entity.reply.QReplyLike.replyLike;

@RequiredArgsConstructor
public class ReplyLikeQueryDslImpl implements ReplyLikeQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public boolean checkUserLikesReply_QueryDSL(Long id, Long userId) {
        Long count = query.select(replyLike.count())
                .from(replyLike)
                .where(replyLike.user.id.eq(userId).and(replyLike.reply.id.eq(id)))
                .fetchOne();

        return count > 0;
    }

    @Override
    public void deleteUserLikeByUserAndReply(Long id, Long userId) {
        query.delete(replyLike)
                .where(replyLike.user.id.eq(userId).and(replyLike.reply.id.eq(id)))
                .execute();
    }
}
