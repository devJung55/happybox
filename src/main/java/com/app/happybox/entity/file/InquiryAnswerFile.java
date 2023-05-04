package com.app.happybox.entity.file;

import com.app.happybox.audity.Period;
import com.app.happybox.entity.customer.InquiryAnswer;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @ToString(exclude = "inquiryAnswer", callSuper = true)
@Table(name = "TBL_INQUIRY_ANSWER_FILE")
@DiscriminatorValue("INQUIRY_ANSWER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InquiryAnswerFile extends Files {
    @ManyToOne(fetch = FetchType.LAZY)
    private InquiryAnswer inquiryAnswer;
}
