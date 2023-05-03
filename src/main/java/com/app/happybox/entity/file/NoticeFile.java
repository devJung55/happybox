package com.app.happybox.entity.file;

import com.app.happybox.entity.customer.Notice;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @ToString
@Table(name = "TBL_NOTICE_FILE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeFile {
    @Id @GeneratedValue @EqualsAndHashCode.Include
    private Long id;
    @NotNull private String filePath;
    @NotNull private String fileUuid;
    private String fileOrgName;
    @Enumerated(EnumType.STRING)
    private FileRepresentType fileRepresentType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Notice notice;
}
