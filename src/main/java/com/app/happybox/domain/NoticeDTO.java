package com.app.happybox.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class NoticeDTO {
    private Long id;
    private String noticeTitle;
    private String noticeContent;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private List<NoticeFileDTO> noticeFileDTOS;
}
