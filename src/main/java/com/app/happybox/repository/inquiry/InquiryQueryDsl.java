package com.app.happybox.repository.inquiry;

import com.app.happybox.entity.customer.Inquiry;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface InquiryQueryDsl {
//    문의 목록
    public Page<Inquiry> findInquiryListByMemberIdWithPaging_QueryDSL(Pageable pageable, Long id);

//    문의 상세
    public Optional<Inquiry> findInquiryByInquiryId_QueryDSL(Long inquiryId);

//    문의 개수
    public Long findInquiryCountByUserId_QueryDSL(Long distributorId);

//    관리자 문의 목록
    public Page<Inquiry> findInquiryListWithPaging_QueryDSL(Pageable pageable);

//    관리자 문의 삭제
    public Long deleteByIds_QueryDSL(List<Long> ids);
}
