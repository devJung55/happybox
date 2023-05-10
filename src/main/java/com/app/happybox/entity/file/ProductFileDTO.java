package com.app.happybox.entity.file;

import com.app.happybox.entity.type.FileRepresent;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class ProductFileDTO {
    private Long id;

    private String filePath;

    private String fileUuid;

    private String fileOrgName;

    private FileRepresent fileRepresent;

    @Builder
    public ProductFileDTO(Long id, String filePath, String fileUuid, String fileOrgName, FileRepresent fileRepresent) {
        this.id = id;
        this.filePath = filePath;
        this.fileUuid = fileUuid;
        this.fileOrgName = fileOrgName;
        this.fileRepresent = fileRepresent;
    }
}
