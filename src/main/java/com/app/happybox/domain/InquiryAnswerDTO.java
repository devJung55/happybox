package com.app.happybox.domain;

import com.app.happybox.entity.customer.Inquiry;
import com.app.happybox.entity.file.InquiryAnswerFile;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class InquiryAnswerDTO {
    private Long id;
    private String inquiryAnswerContent;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private List<InquiryAnswerFileDTO> inquiryAnswerFileDTOS;

    @Builder
    public InquiryAnswerDTO(Long id, String inquiryAnswerContent, LocalDateTime createdDate, LocalDateTime updatedDate, List<InquiryAnswerFileDTO> inquiryAnswerFileDTOS) {
        this.id = id;
        this.inquiryAnswerContent = inquiryAnswerContent;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.inquiryAnswerFileDTOS = inquiryAnswerFileDTOS;
    }
}
