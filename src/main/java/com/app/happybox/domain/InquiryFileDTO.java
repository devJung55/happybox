package com.app.happybox.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InquiryFileDTO {
    private Long id;
    private String filePath;
    private String fileUuid;
    private String fileOrgName;
}
