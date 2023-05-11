package com.app.happybox.repository.reply;

import com.app.happybox.entity.reply.RecipeBoardReply;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.app.happybox.entity.reply.QRecipeBoardReply.recipeBoardReply;

@RequiredArgsConstructor
public class RecipeBoardReplyQueryDslImpl implements RecipeBoardReplyQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public Page<RecipeBoardReply> findAllByMemberIdDescWithPaging_QueryDSL(Pageable pageable, Long memberId) {
        List<RecipeBoardReply> recipeBoardReplyList = query.select(recipeBoardReply)
                .from(recipeBoardReply)
                .where(recipeBoardReply.user.id.eq(memberId))
                .orderBy(recipeBoardReply.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(recipeBoardReply.id.count()).from(recipeBoardReply).where(recipeBoardReply.user.id.eq(memberId)).fetchOne();

        return new PageImpl<>(recipeBoardReplyList, pageable, count);
    }
}
