package com.app.happybox.repository.inquiry;

import com.app.happybox.entity.customer.Inquiry;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.app.happybox.entity.customer.QInquiry.inquiry;

@RequiredArgsConstructor
public class InquiryQueryDslImpl implements InquiryQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public List<Inquiry> findInquiryListByMemberIdWithPaging_QueryDSL(Pageable pageable, Long id) {
        List<Inquiry> inquiryList = query.select(inquiry)
                .from(inquiry)
                .where(inquiry.user.id.eq(id))
                .orderBy(inquiry.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return inquiryList;
    }

    @Override
    public Optional<Inquiry> findInquiryByInquiryId_QueryDSL(Long id) {
        return Optional.ofNullable(
                        query.select(inquiry)
                        .from(inquiry)
                        .join(inquiry.inquiryFiles).fetchJoin()
                        .where(inquiry.id.eq(id))
                        .fetchOne());

    }
}
