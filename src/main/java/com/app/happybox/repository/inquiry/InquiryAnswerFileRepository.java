package com.app.happybox.repository.inquiry;

import com.app.happybox.entity.file.InquiryAnswerFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryAnswerFileRepository extends JpaRepository<InquiryAnswerFile, Long>, InquiryAnswerFileQueryDsl {
}
