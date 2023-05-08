package com.app.happybox.repository.user;

import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.QWelfare;
import com.app.happybox.entity.user.Welfare;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.app.happybox.entity.user.QWelfare.welfare;

@RequiredArgsConstructor
public class WelfareQueryDslImpl implements WelfareQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public void setWelfareInfoById_QueryDSL(Welfare welfare) {
        query.update(QWelfare.welfare)
                .set(QWelfare.welfare.userPassword, welfare.getUserPassword())
                .set(QWelfare.welfare.welfareName, welfare.getWelfareName())
                .set(QWelfare.welfare.userPhoneNumber, welfare.getUserPhoneNumber())
                .set(QWelfare.welfare.address, welfare.getAddress())
                .set(QWelfare.welfare.userEmail, welfare.getUserEmail())
                .execute();
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
