package com.app.happybox.repository.inquiry;

import com.app.happybox.entity.customer.Inquiry;
import com.app.happybox.entity.customer.InquiryAnswer;
import com.app.happybox.entity.type.InquiryStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional @Rollback(false)
@Slf4j
public class InquiryAnswerRepositoryTests {
    @Autowired private InquiryAnswerRepository inquiryAnswerRepository;
    @Autowired private InquiryRepository inquiryRepository;

    @Test
    public void saveTest() {
        Inquiry inquiry = inquiryRepository.findById(17L).get();
        InquiryAnswer inquiryAnswer = new InquiryAnswer("문의내역 답변", inquiry);
        inquiryAnswerRepository.save(inquiryAnswer);
        inquiry.setInquiryStatus(InquiryStatus.COMPLETE);
    }

    @Test
    public void findByInquiryIdTest() {
        inquiryAnswerRepository.findByInquiryId_QueryDSL(
                inquiryRepository.findById(17L).get())
                .stream().map(InquiryAnswer::getInquiryAnswerContent).forEach(log::info);
    }


}
