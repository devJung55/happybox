package com.app.happybox.service.cs;

import com.app.happybox.domain.NoticeDTO;
import com.app.happybox.entity.customer.Notice;
import com.app.happybox.repository.notice.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Primary
@RequiredArgsConstructor
public class CsServiceImpl implements CsService {
    private final NoticeRepository noticeRepository;

    @Override
    public NoticeDTO getNoticeWithId(Long id) {
        Optional<Notice> notice = noticeRepository.findNoticeDetailById_QueryDSL(id);
        return toNoticeDTO(notice.get());
    }
}
