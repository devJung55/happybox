package com.app.happybox.repository.notice;

import com.app.happybox.entity.file.NoticeFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeFileRepository extends JpaRepository<NoticeFile, Long>, NoticeFileQueryDsl {
}
