package com.app.happybox.repository.notice;

import com.app.happybox.entity.customer.Notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface NoticeQueryDsl {
//    공지 목록 조회
    public Page<Notice> findNoticeListWithPaging_QueryDSL(Pageable pageable);
//    공지 상세 조회
    public Optional<Notice> findNoticeDetailById_QueryDSL(Long id);

//    관리자 공지 목록 조회
    public Page<Notice> findNoticeListDescWithPaging_QueryDSL(Pageable pageable);
}
