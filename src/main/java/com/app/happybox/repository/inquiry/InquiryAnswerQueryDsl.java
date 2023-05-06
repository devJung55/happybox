package com.app.happybox.repository.inquiry;

import com.app.happybox.entity.customer.Inquiry;
import com.app.happybox.entity.customer.InquiryAnswer;

import java.util.List;

public interface InquiryAnswerQueryDsl {
//    답변 조회
    public List<InquiryAnswer> findByInquiryId(Inquiry inquiry);
}
