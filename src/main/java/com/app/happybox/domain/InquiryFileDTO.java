package com.app.happybox.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class InquiryFileDTO {
    private Long id;
    private String filePath;
    private String fileUuid;
    private String fileOrgName;

    public InquiryFileDTO(Long id, String filePath, String fileUuid, String fileOrgName) {
        this.id = id;
        this.filePath = filePath;
        this.fileUuid = fileUuid;
        this.fileOrgName = fileOrgName;
    }
}
