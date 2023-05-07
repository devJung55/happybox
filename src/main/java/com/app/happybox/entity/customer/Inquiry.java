package com.app.happybox.entity.customer;

import com.app.happybox.audity.Period;
import com.app.happybox.entity.file.InquiryFile;
import com.app.happybox.entity.type.InquiryStatus;
import com.app.happybox.entity.type.InquiryType;
import com.app.happybox.entity.user.User;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @ToString
@Table(name = "TBL_INQUIRY")
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inquiry extends Period {
    @Id @GeneratedValue @EqualsAndHashCode.Include
    private Long id;
    @NotNull private String inquiryTitle;
    @NotNull private String inquiryContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    /* 문의 유형 */
    @Enumerated(EnumType.STRING)
    private InquiryType inquiryType;

    @Enumerated(EnumType.STRING) @ColumnDefault(value = "'STANDBY'")
    private InquiryStatus inquiryStatus;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, mappedBy = "inquiry")
    private List<InquiryFile> inquiryFiles = new ArrayList<>();

    public Inquiry(String inquiryTitle, String inquiryContent) {
        this.inquiryTitle = inquiryTitle;
        this.inquiryContent = inquiryContent;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setInquiryStatus(InquiryStatus inquiryStatus) {
        this.inquiryStatus = inquiryStatus;
    }
}
