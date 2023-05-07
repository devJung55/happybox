package com.app.happybox.repository.inquiry;

import com.app.happybox.entity.customer.Inquiry;
import com.app.happybox.entity.customer.InquiryAnswer;

import java.util.List;
import java.util.Optional;

public interface InquiryAnswerQueryDsl {
//    답변 상세 조회
    public Optional<InquiryAnswer> findByInquiryId_QueryDSL(Inquiry inquiry);
}
