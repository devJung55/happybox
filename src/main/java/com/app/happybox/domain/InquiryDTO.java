package com.app.happybox.domain;

import com.app.happybox.entity.user.User;
import com.app.happybox.type.InquiryStatus;
import com.app.happybox.type.InquiryType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class InquiryDTO {
    private Long id;
    private String inquiryTitle;
    private String inquiryContent;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private User user;
    private InquiryType inquiryType;
    private InquiryStatus inquiryStatus;
    private List<InquiryFileDTO> inquiryFileDTOS;
}
