package com.app.happybox.repository.inquiry;

import com.app.happybox.entity.customer.InquiryAnswer;
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
        InquiryAnswer inquiryAnswer = new InquiryAnswer("문의내역 답변", inquiryRepository.findById(13L).get());
        inquiryAnswerRepository.save(inquiryAnswer);
    }

    @Test
    public void findByInquiryIdTest() {
        inquiryAnswerRepository.findByInquiryId(
                inquiryRepository.findById(12L).get()).stream().map(InquiryAnswer::getInquiryAnswerContent).forEach(log::info);
    }
}
