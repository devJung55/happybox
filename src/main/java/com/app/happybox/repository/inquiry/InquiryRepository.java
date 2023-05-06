package com.app.happybox.repository.inquiry;

import com.app.happybox.entity.customer.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long>, InquiryQueryDsl {
}
