package com.app.happybox.repository.notice;

import com.app.happybox.entity.customer.Notice;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(false)
@Slf4j
public class NoticeRepositoryTests {
    @Autowired
    private NoticeRepository noticeRepository;

    @Test
    public void saveTest() {
        Notice notice = new Notice("[공지사항] 겨울나기 전 따뜻한 기부소식 공지", "이번에 기부왕으로 선정되신 복지관을 소개드리겠습니다.");
        noticeRepository.save(notice);
    }

    @Test
    public void findNoticeListWithPaging_QueryDSLTest() {
        noticeRepository.findNoticeListWithPaging_QueryDSL(
                PageRequest.of(0, 5)).stream().map(Notice::toString).forEach(log::info);
    }

//    fetchjoin 상세 테스트
    @Test
    public void findNoticeDetailById_QueryDSLTest() {
        noticeRepository.findNoticeDetailById_QueryDSL(23L).stream().map(Notice::toString).forEach(log::info);
    }

//    queryMethod 상세 테스트
    @Test
    public void findNoticeByIdTest() {
        noticeRepository.findNoticeById(23L).ifPresent(notice -> log.info(notice.toString()));
    }
}
