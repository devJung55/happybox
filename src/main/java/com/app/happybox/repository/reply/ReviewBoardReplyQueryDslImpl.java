package com.app.happybox.repository.reply;

import com.app.happybox.entity.reply.QReviewBoardReply;
import com.app.happybox.entity.reply.ReviewBoardReply;
import com.app.happybox.entity.user.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.app.happybox.entity.reply.QReviewBoardReply.reviewBoardReply;

@RequiredArgsConstructor
public class ReviewBoardReplyQueryDslImpl implements ReviewBoardReplyQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public Page<ReviewBoardReply> findAllByMemberIdDescWithPaging_QueryDSL(Pageable pageable, Long memberId) {
        List<ReviewBoardReply> reviewBoardReplyList = query.select(reviewBoardReply)
                .from(reviewBoardReply)
                .where(reviewBoardReply.user.id.eq(memberId))
                .orderBy(reviewBoardReply.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(reviewBoardReply.id.count()).from(reviewBoardReply).where(reviewBoardReply.user.id.eq(memberId)).fetchOne();

        return new PageImpl<>(reviewBoardReplyList, pageable, count);
    }
}