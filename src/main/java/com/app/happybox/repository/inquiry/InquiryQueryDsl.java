package com.app.happybox.repository.inquiry;

import com.app.happybox.entity.customer.Inquiry;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface InquiryQueryDsl {
//    문의 목록
    public List<Inquiry> findInquiryListByMemberIdWithPaging_QueryDSL(Pageable pageable, Long id);

//    문의 상세
    public Optional<Inquiry> findInquiryByInquiryId_QueryDSL(Long id);
}
