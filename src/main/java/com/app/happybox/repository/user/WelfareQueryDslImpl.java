package com.app.happybox.repository.user;

import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.QWelfare;
import com.app.happybox.entity.user.Welfare;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.app.happybox.entity.user.QWelfare.welfare;


@RequiredArgsConstructor
@Slf4j
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

    @Override
    public Slice<Welfare> findByWelfareName_QueryDSL(Pageable pageable, String welfareName) {
        BooleanExpression searchByName = welfareName != null && welfareName != "" ? welfare.welfareName.contains(welfareName) : null;
        List<Welfare> welfareList = query.select(welfare)
                .from(welfare)
                .where(searchByName)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        boolean hasNext = false;

        // 조회한 결과 개수가 요청한 페이지 사이즈보다 크면 뒤에 더 있음, next = true
        if (welfareList.size() > pageable.getPageSize()) {
            hasNext = true;
            welfareList.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(welfareList, pageable, hasNext);
    }
}
