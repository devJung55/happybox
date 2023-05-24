package com.app.happybox.entity.customer;

import com.app.happybox.audity.Period;
import com.app.happybox.entity.file.NoticeFile;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @ToString
@Table(name = "TBL_NOTICE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends Period {
    @Id @GeneratedValue @EqualsAndHashCode.Include
    private Long id;
    @NotNull private String noticeTitle;
    @Column(columnDefinition = "CLOB")
    @NotNull private String noticeContent;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, mappedBy = "notice")
    private List<NoticeFile> noticeFiles = new ArrayList<>();

    public Notice(String noticeTitle, String noticeContent) {
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
    }

    @Builder
    public Notice(Long id, String noticeTitle, String noticeContent, List<NoticeFile> noticeFiles) {
        this.id = id;
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
        this.noticeFiles = noticeFiles;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }
}
