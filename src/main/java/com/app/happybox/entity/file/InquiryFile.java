package com.app.happybox.entity.file;

import com.app.happybox.entity.customer.Inquiry;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @ToString(exclude = "inquiry", callSuper = true)
@Table(name = "TBL_INQUIRY_FILE")
@DiscriminatorValue("INQUIRY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InquiryFile extends Files {
    @ManyToOne(fetch = FetchType.LAZY)
    private Inquiry inquiry;
}
