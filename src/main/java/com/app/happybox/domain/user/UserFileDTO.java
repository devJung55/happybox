package com.app.happybox.domain.user;

import lombok.*;

@Getter @Setter @NoArgsConstructor
public class UserFileDTO {
    private Long id;
    private String filePath;
    private String fileUuid;
    private String fileOrgName;

    @Builder
    public UserFileDTO(Long id, String filePath, String fileUuid, String fileOrgName) {
        this.id = id;
        this.filePath = filePath;
        this.fileUuid = fileUuid;
        this.fileOrgName = fileOrgName;
    }
}
