package com.app.happybox.repository.user;

import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.QWelfare;
import com.app.happybox.entity.user.Welfare;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.app.happybox.entity.user.QMember.member;
import static com.app.happybox.entity.user.QWelfare.welfare;

@RequiredArgsConstructor
public class WelfareQueryDslImpl implements WelfareQueryDsl {
    private final JPAQueryFactory query;

//    복지관 정보 수정
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

//    복지관 이름으로 복지관 정보 조회
    @Override
    public Optional<Welfare> findWelfareByWelfareName(String welfareName) {
        Welfare welfare = query.select(QWelfare.welfare)
                .from(QWelfare.welfare)
                .where(QWelfare.welfare.welfareName.eq(welfareName))
                .fetchOne();
        return Optional.ofNullable(welfare);
    }

//    복지관 Id로 복지관 조회
    @Override
    public Tuple findWelfareInfoById(Long id) {
        return query.select(welfare.userId, welfare.userPassword)
                .from(welfare)
                .where(welfare.id.eq(id))
                .fetchOne();
    }

//    아이디 중복체크(userId를 Return해서 service에서 검사)
    @Override
    public String checkId(String welfareId) {
        return query.select(welfare.userId)
                .from(welfare)
                .where(welfare.userId.eq(welfareId))
                .fetchOne();
    }
}
