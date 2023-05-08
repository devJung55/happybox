package com.app.happybox.repository.inquiry;

import com.app.happybox.entity.customer.Inquiry;
import com.app.happybox.entity.customer.InquiryAnswer;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.app.happybox.entity.customer.QInquiryAnswer.inquiryAnswer;

@RequiredArgsConstructor
public class InquiryAnswerQueryDslImpl implements InquiryAnswerQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public Optional<InquiryAnswer> findByInquiryId_QueryDSL(Inquiry inquiry) {
        return Optional.ofNullable(query
                .select(inquiryAnswer)
                .from(inquiryAnswer)
                .join(inquiryAnswer.inquiryAnswerFiles).fetchJoin()
                .where(inquiryAnswer.inquiry.eq(inquiry)).fetchOne());
    }
}
