package com.app.happybox.repository.subscript;

import com.app.happybox.entity.subscript.Rider;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.app.happybox.entity.subscript.QRider.rider;

@RequiredArgsConstructor
public class RiderQueryDslImpl implements RiderQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public Page<Rider> findAllByWelfareIdWithPaging_QueryDSL(Pageable pageable, Long welfareId) {
        List<Rider> riderList = query.select(rider)
                .from(rider)
                .where(rider.welfare.id.eq(welfareId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(rider.id.count()).from(rider).where(rider.welfare.id.eq(welfareId)).fetchOne();

        return new PageImpl<>(riderList, pageable, count);
    }
}
