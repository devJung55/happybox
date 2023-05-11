package com.app.happybox.repository.inquiry;

import com.app.happybox.entity.customer.Inquiry;
import com.app.happybox.entity.customer.InquiryAnswer;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.app.happybox.entity.customer.QInquiryAnswer.inquiryAnswer;

@RequiredArgsConstructor
public class InquiryAnswerQueryDslImpl implements InquiryAnswerQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public Page<InquiryAnswer> findAnswersByInquiryId_QueryDSL(Pageable pageable, Long id) {
        List<InquiryAnswer> inquiryAnswers = query.select(inquiryAnswer)
                .from(inquiryAnswer)
                .where(inquiryAnswer.inquiry.id.eq(id))
                .orderBy(inquiryAnswer.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        Long count = query.select(inquiryAnswer.count()).from(inquiryAnswer).fetchOne();
        return new PageImpl<>(inquiryAnswers, pageable, count);
    }

    @Override
    public Optional<InquiryAnswer> findByInquiryId_QueryDSL(Inquiry inquiry) {
        return Optional.ofNullable(query
                .select(inquiryAnswer)
                .from(inquiryAnswer)
                .join(inquiryAnswer.inquiryAnswerFiles).fetchJoin()
                .where(inquiryAnswer.inquiry.eq(inquiry)).fetchOne());
    }

    @Override
    public List<InquiryAnswer> findByUserId_QueryDSL(Long userId) {
        return query.select(inquiryAnswer)
                .from(inquiryAnswer)
                .join(inquiryAnswer.inquiry).fetchJoin()
                .where(inquiryAnswer.inquiry.user.id.eq(userId))
                .fetch();
    }
}
