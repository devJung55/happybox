package com.app.happybox.entity.customer;

import com.app.happybox.audity.Period;
import com.app.happybox.entity.file.InquiryFile;
import com.app.happybox.entity.type.InquiryStatus;
import com.app.happybox.entity.type.InquiryType;
import com.app.happybox.entity.user.User;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @ToString
@Table(name = "TBL_INQUIRY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inquiry extends Period {
    @Id @GeneratedValue @EqualsAndHashCode.Include
    private Long id;
    @NotNull private String inquiryTitle;
    @NotNull private String inquiryContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    /* 문의 유현 */
    @Enumerated(EnumType.STRING)
    private InquiryType inquiryType;

    @Enumerated(EnumType.STRING)
    private InquiryStatus inquiryStatus;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "inquiry", cascade = CascadeType.REMOVE)
    private InquiryAnswer inquiryAnswer;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, mappedBy = "inquiry")
    private List<InquiryFile> inquiryFiles = new ArrayList<>();
}
