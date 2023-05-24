package com.app.happybox.entity.customer;

import com.app.happybox.audity.Period;
import com.app.happybox.entity.file.InquiryFile;
import com.app.happybox.type.InquiryStatus;
import com.app.happybox.type.InquiryType;
import com.app.happybox.entity.user.User;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @ToString(exclude = "user")
@Table(name = "TBL_INQUIRY")
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inquiry extends Period {
    @Id @GeneratedValue @EqualsAndHashCode.Include
    private Long id;
    @NotNull private String inquiryTitle;
    @Column(columnDefinition = "CLOB")
    @NotNull private String inquiryContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    /* 문의 유형 */
    @Enumerated(EnumType.STRING)
    private InquiryType inquiryType;

    @Enumerated(EnumType.STRING) @ColumnDefault(value = "'STANDBY'")
    private InquiryStatus inquiryStatus;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, orphanRemoval = true, mappedBy = "inquiry")
    private List<InquiryFile> inquiryFiles = new ArrayList<>();

    public Inquiry(String inquiryTitle, String inquiryContent, InquiryType inquiryType) {
        this.inquiryTitle = inquiryTitle;
        this.inquiryContent = inquiryContent;
        this.inquiryType = inquiryType;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setInquiryStatus(InquiryStatus inquiryStatus) {
        this.inquiryStatus = inquiryStatus;
    }

    @Builder // DTO entity로 만들기 위한 빌더
    public Inquiry(Long id, String inquiryTitle, String inquiryContent, User user, InquiryType inquiryType, List<InquiryFile> inquiryFiles) {
        this.id = id;
        this.inquiryTitle = inquiryTitle;
        this.inquiryContent = inquiryContent;
        this.user = user;
        this.inquiryType = inquiryType;
        this.inquiryFiles = inquiryFiles;
    }
}
