package com.app.happybox.repository.user;

import com.app.happybox.entity.user.UserRandomKey;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.app.happybox.entity.user.QUser.user;
import static com.app.happybox.entity.user.QUserRandomKey.userRandomKey;

@RequiredArgsConstructor
public class RandomKeyQueryDslImpl implements RandomKeyQueryDsl {
    private final JPAQueryFactory query;

    //    회원의 가장 최근 랜덤키 찾기
    @Override
    public UserRandomKey getLatestRandomKey_QueryDSL(Long id) {
        return query.select(userRandomKey)
                .from(user)
                .join(user.userRandomKeys, userRandomKey)
                .where(user.id.eq(id))
                .orderBy(userRandomKey.id.desc())
                .select(userRandomKey)
                .fetchFirst();
    }

}
