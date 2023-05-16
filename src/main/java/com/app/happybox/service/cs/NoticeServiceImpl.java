package com.app.happybox.service.cs;

import com.app.happybox.domain.NoticeDTO;
import com.app.happybox.entity.customer.Notice;
import com.app.happybox.entity.customer.NoticeSearch;
import com.app.happybox.entity.file.NoticeFile;
import com.app.happybox.repository.notice.NoticeFileRepository;
import com.app.happybox.repository.notice.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Qualifier("notice")
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;
    private final NoticeFileRepository noticeFileRepository;

//    공지 목록 조회
    @Override
    public Page<NoticeDTO> getNoticeList(Pageable pageable, NoticeSearch noticeSearch) {
        Page<Notice> notices = noticeRepository.findNoticeListWithPaging_QueryDSL(pageable, noticeSearch);
        List<NoticeDTO> noticeList = notices.get().map(this::toNoticeDTO).collect(Collectors.toList());
        return new PageImpl<>(noticeList, pageable, notices.getTotalElements());
    }

//    공지 상세보기
    @Override
    public NoticeDTO getNoticeWithId(Long id) {
        Optional<Notice> notice = noticeRepository.findNoticeDetailById_QueryDSL(id);
        return toNoticeDTO(notice.get());
    }

    //    관리자용 목록 조회
    @Override
    public Page<NoticeDTO> getAdminNoticeList(Pageable pageable) {
        Page<Notice> notices = noticeRepository.findNoticeListDescWithPaging_QueryDSL(pageable);
        List<NoticeDTO> noticeList = notices.get().map(this::toNoticeDTO).collect(Collectors.toList());
        return new PageImpl<>(noticeList, pageable, notices.getTotalElements());
    }

    //    공지 삭제
    @Override
    public void deleteById(Long id) {
        noticeRepository.findById(id).ifPresent(notice -> noticeRepository.delete(notice));
    }

    //    공지 등록
    @Override
    @Transactional
    public void noticeWrite(NoticeDTO noticeDTO) {
        Notice notice = toNoticeEntity(noticeDTO);
        noticeRepository.save(notice);
        List<NoticeFile> noticeFiles = toNoticeEntity(noticeDTO).getNoticeFiles();
        noticeFiles.forEach(noticeFile -> noticeFile.setNotice(notice));
        noticeFileRepository.saveAll(noticeFiles);
    }
}
