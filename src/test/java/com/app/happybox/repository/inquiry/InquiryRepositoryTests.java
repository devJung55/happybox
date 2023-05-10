package com.app.happybox.repository.inquiry;

import com.app.happybox.entity.customer.Inquiry;
import com.app.happybox.entity.file.InquiryFile;
import com.app.happybox.type.FileRepresent;
import com.app.happybox.type.InquiryType;
import com.app.happybox.entity.user.User;
import com.app.happybox.repository.user.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@SpringBootTest
@Transactional @Rollback(false)
@Slf4j
public class InquiryRepositoryTests {
    @Autowired private InquiryRepository inquiryRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private InquiryFileRepository inquiryFileRepository;

    @Test
    public void saveTest() {
        InquiryType[] inquiryTypes = {InquiryType.ORDER, InquiryType.CANCEL, InquiryType.SITE, InquiryType.USE, InquiryType.ETC};
        for (int i = 0; i < 3; i++) {
            Inquiry inquiry = new Inquiry("문의 제목" + (i + 1), "문의 내용" + (i + 1), inquiryTypes[new Random().nextInt(inquiryTypes.length)]);
            memberRepository.findById(43L).ifPresent(member -> inquiry.setUser((User)member));

            inquiryRepository.save(inquiry);
        }
    }

    @Test
    public void fileSaveTest() {
        Inquiry inquiry = inquiryRepository.findById(53L).get();

        InquiryFile inquiryFile1 = new InquiryFile("2023/05/09", UUID.randomUUID().toString(), "문의사항1.png", FileRepresent.REPRESENT);
        InquiryFile inquiryFile2 = new InquiryFile("2023/05/09", UUID.randomUUID().toString(), "문의사항2.png", FileRepresent.ORDINARY);
        InquiryFile inquiryFile3 = new InquiryFile("2023/05/09", UUID.randomUUID().toString(), "문의사항3.png", FileRepresent.ORDINARY);

        List<InquiryFile> inquiryFiles = new ArrayList<>(Arrays.asList(inquiryFile1, inquiryFile2, inquiryFile3));

        inquiryFile1.setInquiry(inquiry);
        inquiryFile2.setInquiry(inquiry);
        inquiryFile3.setInquiry(inquiry);

        inquiryFileRepository.save(inquiryFile1);
        inquiryFileRepository.save(inquiryFile2);
        inquiryFileRepository.save(inquiryFile3);
    }

    @Test
    public void findInquiryListByMemberIdWithPaging_QueryDSLTest() {
        inquiryRepository.findInquiryListByMemberIdWithPaging_QueryDSL(
                PageRequest.of(0, 5), 1L).stream().forEach(v -> {
                    log.info(v.getInquiryTitle());
                    log.info(v.getInquiryContent());
                    log.info(v.getInquiryFiles().toString());
        });
    }

    @Test
    public void findInquiryByInquiryId_QueryDSLTest() {
        log.info(inquiryRepository.findInquiryByInquiryId_QueryDSL(2L).get().toString());
    }

    @Test
    public void findInquiryListWithPaging_QueryDSL_Test() {
        inquiryRepository.findInquiryListWithPaging_QueryDSL(PageRequest.of(0, 10))
                .stream().forEach(inquiry -> {
                    log.info(inquiry.getInquiryContent());
                    log.info(inquiry.getInquiryTitle());
                    log.info(inquiry.getInquiryFiles().toString());
        });
    }
}
