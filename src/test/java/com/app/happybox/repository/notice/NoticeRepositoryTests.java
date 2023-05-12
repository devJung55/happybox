package com.app.happybox.repository.notice;

import com.app.happybox.domain.NoticeDTO;
import com.app.happybox.entity.customer.Notice;
import com.app.happybox.entity.customer.NoticeSearch;
import com.app.happybox.entity.file.NoticeFile;
import com.app.happybox.type.FileRepresent;
import com.app.happybox.service.cs.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@Transactional
@Rollback(false)
@Slf4j
public class NoticeRepositoryTests {
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private NoticeFileRepository noticeFileRepository;
    @Autowired
    private NoticeService noticeService;

    @Test
    public void saveTest() {
//        Notice notice = new Notice("[공지사항] 겨울나기 전 따뜻한 기부소식 공지", "이번에 기부왕으로 선정되신 복지관을 소개드리겠습니다.");
//        Notice notice = new Notice("[훈훈소식] 이번달의 기부왕!!!", "이번달에 기부왕으로 선정된 복지관은 어디일까요.");
        Notice notice = new Notice("[중요!] 태풍 피해 복구 예상 기간", "태풍으로 인해 입은 피해를 최대한 빨리 복구중입니다.");
        noticeRepository.save(notice);
    }

    @Test
    public void fileSaveTest() {
        Notice notice = noticeRepository.findById(166L).get();
        NoticeFile noticeFile1 = new NoticeFile("2023/05/09", UUID.randomUUID().toString(), "공지사항1.png", FileRepresent.REPRESENT);
        NoticeFile noticeFile2 = new NoticeFile("2023/05/09", UUID.randomUUID().toString(), "공지사항2.png", FileRepresent.ORDINARY);
        NoticeFile noticeFile3 = new NoticeFile("2023/05/09", UUID.randomUUID().toString(), "공지사항3.png", FileRepresent.ORDINARY);
        List<NoticeFile> noticeFiles = new ArrayList<>(Arrays.asList(noticeFile1, noticeFile2, noticeFile3));
        noticeFile1.setNotice(notice);
        noticeFile2.setNotice(notice);
        noticeFile3.setNotice(notice);

        noticeFileRepository.saveAll(noticeFiles);
    }

    @Test
    public void findNoticeListWithPaging_QueryDSLTest() {
        noticeRepository.findNoticeListWithPaging_QueryDSL(
                PageRequest.of(0, 5), new NoticeSearch()).stream().map(Notice::toString).forEach(log::info);
    }

    //    fetchjoin 상세 테스트
    @Test
    public void findNoticeDetailById_QueryDSLTest() {
        noticeRepository.findNoticeDetailById_QueryDSL(33L).stream().map(Notice::toString).forEach(log::info);
        noticeRepository.findById(33L).get().getNoticeFiles().toString();
    }

    //    queryMethod 상세 테스트
    @Test
    public void findNoticeByIdTest() {
        noticeRepository.findNoticeById(23L).ifPresent(notice -> log.info(notice.toString()));
    }

    @Test
    public void getNoticeListTest() {
        PageRequest page = PageRequest.of(0, 5);
        NoticeSearch noticeSearch = new NoticeSearch();
//        noticeSearch.setNoticeTitle("공지");
        noticeService.getNoticeList(page, noticeSearch).stream().map(NoticeDTO::toString).forEach(log::info);
    }
}
