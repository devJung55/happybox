package com.app.happybox.repository.user;

import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.QWelfare;
import com.app.happybox.entity.user.Welfare;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.app.happybox.entity.user.QWelfare.welfare;


@RequiredArgsConstructor
public class WelfareQueryDslImpl implements WelfareQueryDsl {
    private final JPAQueryFactory query;

//    복지관 정보수정
    @Override
    @Transactional
    public void setWelfareInfoById_QueryDSL(Welfare welfare) {
        JPAUpdateClause updateClause = query.update(QWelfare.welfare);

        updateClause
                .set(QWelfare.welfare.userPassword, welfare.getUserPassword())
                .set(QWelfare.welfare.welfareName, welfare.getWelfareName())
                .set(QWelfare.welfare.userPhoneNumber, welfare.getUserPhoneNumber())
                .set(QWelfare.welfare.address, welfare.getAddress())
                .set(QWelfare.welfare.userEmail, welfare.getUserEmail())
                .execute();
    }

//    복지관 로그인
    @Override
    public Optional<Welfare> logIn(String welfareId, String welfarePassword) {
        return Optional.ofNullable(query.select(welfare)
                .from(welfare)
                .where(welfare.userId.eq(welfareId).and(welfare.userPassword.eq(welfarePassword)))
                .fetchOne());
    }

    @Override
    public Page<Welfare> findAllWithPaging_QueryDSL(Pageable pageable) {
        List<Welfare> welfareList = query.select(welfare)
                .from(welfare)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(welfare.id.count()).from(welfare).fetchOne();

        return new PageImpl<>(welfareList, pageable, count);
    }

    @Override
    public Optional<Welfare> findWelfareById_QueryDSL(Long welfareId) {
        Optional<Welfare> welfare = Optional.ofNullable(query.select(QWelfare.welfare)
                .from(QWelfare.welfare)
                .where(QWelfare.welfare.id.eq(welfareId))
                .fetchOne());

        return welfare;
    }
}
