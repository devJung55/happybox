package com.app.happybox.entity.file;

import com.app.happybox.entity.customer.Notice;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @ToString(exclude = "notice", callSuper = true)
@Table(name = "TBL_NOTICE_FILE")
@DiscriminatorValue("NOTICE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeFile extends Files {
    @ManyToOne(fetch = FetchType.LAZY)
    private Notice notice;
}
