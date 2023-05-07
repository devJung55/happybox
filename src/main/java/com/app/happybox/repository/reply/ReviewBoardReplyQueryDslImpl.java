package com.app.happybox.repository.reply;

import com.app.happybox.entity.reply.QReviewBoardReply;
import com.app.happybox.entity.reply.ReviewBoardReply;
import com.app.happybox.entity.user.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.app.happybox.entity.reply.QReviewBoardReply.reviewBoardReply;

@RequiredArgsConstructor
public class ReviewBoardReplyQueryDslImpl implements ReviewBoardReplyQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public List<ReviewBoardReply> findAllByMemberIdDescWithPaging_QueryDSL(Member member) {
        List<ReviewBoardReply> reviewBoardReplyList = query.select(reviewBoardReply)
                .from(reviewBoardReply)
                .where(reviewBoardReply.user.eq(member))
                .orderBy(reviewBoardReply.id.desc())
                .fetch();

        return reviewBoardReplyList;
    }
}