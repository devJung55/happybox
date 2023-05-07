package com.app.happybox.repository.user;

import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.Welfare;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class WelfareQueryDslImpl implements WelfareQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public void setWelfareInfoById_QueryDSL(Welfare welfare) {

    }

    @Override
    public void setWelfareStatusById_QueryDSL(Welfare welfare) {

    }

    @Override
    public Optional<Member> findWelfareByWelfarePhone(String welfarePhone) {
        return Optional.empty();
    }

    @Override
    public Tuple findWelfareInfoById(Long id) {
        return null;
    }

    @Override
    public Boolean checkId(String welfareId) {
        return null;
    }
}
