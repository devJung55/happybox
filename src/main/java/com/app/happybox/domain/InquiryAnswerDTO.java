package com.app.happybox.domain;

import com.app.happybox.entity.customer.Inquiry;
import com.app.happybox.entity.file.InquiryAnswerFile;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class InquiryAnswerDTO {
    private Long id;
    private String inquiryAnswerContent;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private List<InquiryAnswerFileDTO> inquiryAnswerFileDTOS;
}
