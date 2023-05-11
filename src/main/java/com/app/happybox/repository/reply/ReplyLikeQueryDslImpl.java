package com.app.happybox.repository.reply;

import com.app.happybox.entity.reply.Reply;
import com.app.happybox.entity.reply.ReplyLike;
import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.app.happybox.entity.reply.QReplyLike.replyLike;

@RequiredArgsConstructor
public class ReplyLikeQueryDslImpl implements ReplyLikeQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public boolean checkUserLikesReply_QueryDSL(User user, Reply reply) {
        Long count = query.select(replyLike.count())
                .from(replyLike)
                .where(replyLike.user.eq(user).and(replyLike.reply.eq(reply)))
                .fetchOne();

        return count > 0;
    }

    @Override
    public void deleteUserLikeByUserAndReply(User user, Reply reply) {
        query.delete(replyLike)
                .where(replyLike.user.eq(user).and(replyLike.reply.eq(reply)))
                .execute();
    }
}
