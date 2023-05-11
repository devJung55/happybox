package com.app.happybox.repository.inquiry;

import com.app.happybox.entity.customer.Inquiry;
import com.app.happybox.entity.customer.InquiryAnswer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface InquiryAnswerQueryDsl {
//    답변 목록 조회
    public Page<InquiryAnswer> findAnswersByInquiryId_QueryDSL(Pageable pageable, Long id);

//    답변 상세 조회
    public Optional<InquiryAnswer> findByInquiryId_QueryDSL(Inquiry inquiry);
}
