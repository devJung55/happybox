package com.app.happybox.repository.inquiry;

import com.app.happybox.entity.file.InquiryFile;

import java.util.List;

public interface InquiryFileQueryDsl {
    //    파일 가져오기
    public List<InquiryFile> findByInquiryId(Long id);
}
