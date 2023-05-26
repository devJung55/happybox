package com.app.happybox.repository.inquiry;

import com.app.happybox.entity.customer.Inquiry;
import com.app.happybox.entity.customer.QInquiry;
import com.app.happybox.entity.customer.QInquiryAnswer;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.app.happybox.entity.customer.QInquiry.inquiry;
import static com.app.happybox.entity.customer.QInquiryAnswer.inquiryAnswer;


@RequiredArgsConstructor
public class InquiryQueryDslImpl implements InquiryQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public Page<Inquiry> findInquiryListByMemberIdWithPaging_QueryDSL(Pageable pageable, Long memberId) {
        List<Inquiry> inquiryList = query.select(inquiry)
                .from(inquiry)
                .leftJoin(inquiry.inquiryFiles).fetchJoin()
                .where(inquiry.user.id.eq(memberId))
                .orderBy(inquiry.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(inquiry.id.count()).from(inquiry).where(inquiry.user.id.eq(memberId)).fetchOne();

        return new PageImpl<>(inquiryList, pageable, count);
    }

    @Override
    public Optional<Inquiry> findInquiryByInquiryId_QueryDSL(Long inquiryId) {
        return Optional.ofNullable(
                query.select(inquiry)
                        .from(inquiry)
                        .leftJoin(inquiry.inquiryFiles).fetchJoin()
                        .join(inquiry.user).fetchJoin()
                        .where(inquiry.id.eq(inquiryId))
                        .fetchOne());
    }

    @Override
    public Long findInquiryCountByUserId_QueryDSL(Long id) {
        Long count = query.select(inquiry.id.count())
                .from(inquiry)
                .where(inquiry.user.id.eq(id))
                .fetchOne();

        return count;
    }

    @Override
    public Page<Inquiry> findInquiryListWithPaging_QueryDSL(Pageable pageable) {
        List<Inquiry> inquiryList = query.select(inquiry)
                .from(inquiry)
                .join(inquiry.user).fetchJoin()
                .orderBy(inquiry.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(inquiry.id.count()).from(inquiry).fetchOne();

        return new PageImpl<>(inquiryList, pageable, count);
    }

    @Override
    public Long deleteByIds_QueryDSL(List<Long> ids) {
        query.delete(inquiryAnswer)
                .where(inquiryAnswer.inquiry.id.in(ids))
                .execute();

        return query.delete(inquiry)
                .where(inquiry.id.in(ids))
                .execute();
    }
}
