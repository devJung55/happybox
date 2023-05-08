package com.app.happybox.entity.customer;

import com.app.happybox.entity.file.NoticeFile;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NoticeDTO {
    private Long id;
    private String noticeTitle;
    private String noticeContent;
    private List<NoticeFile> noticeFile;
}
