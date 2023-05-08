package com.app.happybox.entity.file;

import com.app.happybox.entity.customer.Inquiry;
import com.app.happybox.entity.type.FileRepresent;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @ToString(exclude = "inquiry", callSuper = true)
@Table(name = "TBL_INQUIRY_FILE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InquiryFile extends Files {
    @Enumerated(EnumType.STRING)
    private FileRepresent fileRepresent;

    @ManyToOne(fetch = FetchType.LAZY)
    private Inquiry inquiry;

    public InquiryFile(String filePath, String fileUuid, String fileOrgName, FileRepresent fileRepresent) {
        super(filePath, fileUuid, fileOrgName);
        this.fileRepresent = fileRepresent;
    }

    public void setInquiry(Inquiry inquiry) {
        this.inquiry = inquiry;
    }
}
