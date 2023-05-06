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
    public List<InquiryAnswer> findByInquiryId(Inquiry inquiry) {
        List<InquiryAnswer> inquiryAnswerList = query
                .select(inquiryAnswer)
                .from(inquiryAnswer)
                .where(inquiryAnswer.inquiry.eq(inquiry)).fetch();

        return inquiryAnswerList;
    }
}
