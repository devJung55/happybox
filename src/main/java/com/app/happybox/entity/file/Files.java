package com.app.happybox.entity.file;

import com.app.happybox.audity.Period;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity @Table(name = "TBL_FILES")
@Getter @ToString @NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorColumn(name = "FILE_REF")
@Inheritance(strategy = InheritanceType.JOINED)
public class Files extends Period {
    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;

    @NotNull
    private String filePath;
    @NotNull
    private String fileUuid;
    @NotNull
    private String fileOrgName;
}
