package com.app.happybox.entity.file;

import com.app.happybox.audity.Period;
import com.app.happybox.entity.customer.InquiryAnswer;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @ToString
@Table(name = "TBL_INQUIRY_ANSWER_FILE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InquiryAnswerFile extends Period {
    @Id @GeneratedValue @EqualsAndHashCode.Include
    private Long id;
    @NotNull private String filePath;
    @NotNull private String fileUuid;
    private String fileOrgName;
    @Enumerated(EnumType.STRING)
    private FileRepresentType fileRepresentType;

    @ManyToOne(fetch = FetchType.LAZY)
    private InquiryAnswer inquiryAnswer;
}
