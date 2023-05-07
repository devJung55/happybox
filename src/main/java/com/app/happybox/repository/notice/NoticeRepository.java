package com.app.happybox.repository.notice;

import com.app.happybox.entity.customer.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long>, NoticeQueryDsl {
//    공지 상세 queryMethod
    public Optional<Notice> findNoticeById(Long id);

}
