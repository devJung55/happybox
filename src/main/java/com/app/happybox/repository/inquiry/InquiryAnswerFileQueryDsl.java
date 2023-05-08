package com.app.happybox.repository.inquiry;

import com.app.happybox.entity.file.InquiryAnswerFile;

import java.util.List;

public interface InquiryAnswerFileQueryDsl {
    //    파일 가져오기
    public List<InquiryAnswerFile> findByInquiryAnswerId(Long id);
}
