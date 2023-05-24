package com.app.happybox.repository.subscript;

import com.app.happybox.entity.subscript.Rider;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.app.happybox.entity.subscript.QRider.rider;

@RequiredArgsConstructor @Slf4j
public class RiderQueryDslImpl implements RiderQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public Page<Rider> findAllByWelfareIdWithPaging_QueryDSL(Pageable pageable, Long welfareId) {
        List<Rider> riderList = query.select(rider)
                .from(rider)
                .where(rider.welfare.id.eq(welfareId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(rider.id.desc())
                .fetch();

        Long count = query
                .select(rider.id.count())
                .from(rider)
                .where(rider.welfare.id.eq(welfareId))
                .fetchOne();
        return new PageImpl<>(riderList, pageable, count);
    }

    @Override
    public Long findRiderCount_QueryDSL(Long welfareId) {
        Long count = query
                .select(rider.id.count())
                .from(rider)
                .where(rider.welfare.id.eq(welfareId))
                .fetchOne();
        return count;
    }
}
