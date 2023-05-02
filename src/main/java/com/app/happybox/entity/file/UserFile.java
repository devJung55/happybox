package com.app.happybox.entity.file;

import com.app.happybox.audity.Period;
import com.app.happybox.entity.user.User;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Table(name = "TBL_USER_FILE")
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserFile extends Period implements FileTable {
    @Id @GeneratedValue
    private Long id;

    @NotNull
    private String filePath;
    @NotNull
    private String fileUuid;
    @NotNull
    private String fileOrgName;
    @Enumerated(EnumType.STRING)
    private FileType fileType;
    @Enumerated(EnumType.STRING)
    private FileRepresentType fileRepresentType;

}
