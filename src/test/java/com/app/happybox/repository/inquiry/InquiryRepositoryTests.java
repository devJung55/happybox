package com.app.happybox.repository.inquiry;

import com.app.happybox.entity.customer.Inquiry;
import com.app.happybox.entity.user.User;
import com.app.happybox.repository.user.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional @Rollback(false)
@Slf4j
public class InquiryRepositoryTests {
    @Autowired private InquiryRepository inquiryRepository;
    @Autowired private MemberRepository memberRepository;

    @Test
    public void saveTest() {
        for (int i = 0; i < 5; i++) {
            Inquiry inquiry = new Inquiry("문의 제목" + (i + 1), "문의 내용" + (i + 1));
            memberRepository.findById(1L).ifPresent(member -> inquiry.setUser((User)member));

            inquiryRepository.save(inquiry);
        }
    }

    @Test
    public void findInquiryListByMemberIdWithPaging_QueryDSLTest() {
        inquiryRepository.findInquiryListByMemberIdWithPaging_QueryDSL(
                PageRequest.of(0, 5), 42L).stream().map(Inquiry::getInquiryTitle).forEach(log::info);
    }

    @Test
    public void findInquiryByInquiryId_QueryDSLTest() {
        log.info(inquiryRepository.findInquiryByInquiryId_QueryDSL(17L).toString());
    }
}
