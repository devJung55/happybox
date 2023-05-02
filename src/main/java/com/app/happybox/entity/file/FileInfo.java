package com.app.happybox.entity.file;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileInfo {
    private String filePath;
    private String fileUuid;
    private String fileOrgName;
    @Enumerated(EnumType.STRING)
    private FileType fileType;
}
