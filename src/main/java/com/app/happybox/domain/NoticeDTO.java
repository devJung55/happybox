package com.app.happybox.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NoticeDTO {
    private Long id;
    private String noticeTitle;
    private String noticeContent;
    private List<NoticeFileDTO> noticeFileDTOS;
}
