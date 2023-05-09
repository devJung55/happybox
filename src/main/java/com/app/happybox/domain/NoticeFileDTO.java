package com.app.happybox.domain;

import com.app.happybox.entity.type.FileRepresent;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoticeFileDTO {
    private Long id;
    private String filePath;
    private String fileUuid;
    private String fileOrgName;
    private FileRepresent fileRepresent;
}
