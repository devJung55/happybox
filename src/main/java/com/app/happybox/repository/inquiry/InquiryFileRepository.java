package com.app.happybox.repository.inquiry;

import com.app.happybox.entity.file.InquiryFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryFileRepository extends JpaRepository<InquiryFile, Long>, InquiryFileQueryDsl {
}
