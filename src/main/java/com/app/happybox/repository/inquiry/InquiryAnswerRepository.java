package com.app.happybox.repository.inquiry;

import com.app.happybox.entity.customer.InquiryAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryAnswerRepository extends JpaRepository<InquiryAnswer, Long>, InquiryAnswerQueryDsl {
}
