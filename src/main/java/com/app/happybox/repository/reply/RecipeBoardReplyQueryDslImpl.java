package com.app.happybox.repository.reply;

import com.app.happybox.entity.reply.QRecipeBoardReply;
import com.app.happybox.entity.reply.RecipeBoardReply;
import com.app.happybox.entity.user.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.app.happybox.entity.reply.QRecipeBoardReply.recipeBoardReply;

@RequiredArgsConstructor
public class RecipeBoardReplyQueryDslImpl implements RecipeBoardReplyQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public List<RecipeBoardReply> findAllByMemberIdDescWithPaging_QueryDSL(Member member) {
        List<RecipeBoardReply> recipeBoardReplyList = query.select(recipeBoardReply)
                .from(recipeBoardReply)
                .where(recipeBoardReply.user.eq(member))
                .orderBy(recipeBoardReply.id.desc())
                .fetch();

        return recipeBoardReplyList;
    }
}
