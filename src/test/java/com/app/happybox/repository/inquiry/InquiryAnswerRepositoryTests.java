package com.app.happybox.repository.inquiry;

import com.app.happybox.domain.InquiryAnswerDTO;
import com.app.happybox.entity.customer.Inquiry;
import com.app.happybox.entity.customer.InquiryAnswer;
import com.app.happybox.entity.file.InquiryAnswerFile;
import com.app.happybox.type.FileRepresent;
import com.app.happybox.type.InquiryStatus;
import com.app.happybox.service.cs.InquiryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@Transactional @Rollback(false)
@Slf4j
public class InquiryAnswerRepositoryTests {
    @Autowired private InquiryAnswerRepository inquiryAnswerRepository;
    @Autowired private InquiryRepository inquiryRepository;
    @Autowired private InquiryAnswerFileRepository inquiryAnswerFileRepository;
    @Autowired private InquiryService inquiryService;

    @Test
    public void saveTest() {
        Inquiry inquiry = inquiryRepository.findById(91L).get();
        InquiryAnswer inquiryAnswer = new InquiryAnswer("문의내역 답변", inquiry);
        inquiryAnswerRepository.save(inquiryAnswer);
        inquiry.setInquiryStatus(InquiryStatus.COMPLETE);

        InquiryAnswerFile inquiryAnswerFile1 = new InquiryAnswerFile("2023/05/09", UUID.randomUUID().toString(), "답변사항1.png", FileRepresent.REPRESENT);
        InquiryAnswerFile inquiryAnswerFile2 = new InquiryAnswerFile("2023/05/09", UUID.randomUUID().toString(), "답변사항2.png", FileRepresent.ORDINARY);
        InquiryAnswerFile inquiryAnswerFile3 = new InquiryAnswerFile("2023/05/09", UUID.randomUUID().toString(), "답변사항3.png", FileRepresent.ORDINARY);

        inquiryAnswerFile1.setInquiryAnswer(inquiryAnswer);
        inquiryAnswerFile2.setInquiryAnswer(inquiryAnswer);
        inquiryAnswerFile3.setInquiryAnswer(inquiryAnswer);

        inquiryAnswerFileRepository.save(inquiryAnswerFile1);
        inquiryAnswerFileRepository.save(inquiryAnswerFile2);
        inquiryAnswerFileRepository.save(inquiryAnswerFile3);
    }

    @Test
    public void findByInquiryIdTest() {
        inquiryAnswerRepository.findByInquiryId_QueryDSL(
                inquiryRepository.findById(91L).get())
                .stream().map(InquiryAnswer::toString).forEach(log::info);
    }

    @Test
    public void findAnswersByInquiryId_QueryDSLTest() {
        PageRequest page = PageRequest.of(0, 5);
        inquiryAnswerRepository.findAnswersByInquiryId_QueryDSL(page, 91L).stream().map(InquiryAnswer::toString).forEach(log::info);
    }

    @Test
    public void getInquiryAnswerListByIdTest() {
        PageRequest page = PageRequest.of(0, 5);
        inquiryService.getInquiryAnswerListById(page, 91L).stream().map(InquiryAnswerDTO::toString).forEach(log::info);
    }

    @Test
    public void getListByMemberId_Test() {
        inquiryAnswerRepository.findByUserId_QueryDSL(1L).stream().map(InquiryAnswer::getInquiry).forEach(v -> log.info(v.getId() + ""));
    }
}
