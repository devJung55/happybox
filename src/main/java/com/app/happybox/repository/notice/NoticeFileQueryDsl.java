package com.app.happybox.repository.notice;

import com.app.happybox.entity.file.NoticeFile;

import java.util.List;

public interface NoticeFileQueryDsl {
//    파일 가져오기
    public List<NoticeFile> findByNoticeId(Long id);
}
