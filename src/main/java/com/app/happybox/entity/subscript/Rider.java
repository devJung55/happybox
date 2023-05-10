package com.app.happybox.entity.subscript;

import com.app.happybox.audity.Period;
import com.app.happybox.entity.type.DeliveryType;
import com.app.happybox.entity.user.Welfare;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter @ToString
@Table(name = "TBL_RIDER")
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Rider extends Period {
    @Id @GeneratedValue @EqualsAndHashCode.Include
    private Long id;
    @NotNull private String riderName;
    @Column(unique = true)
    private String riderPhoneNumber;
    @Enumerated(EnumType.STRING)
    @ColumnDefault(value = "'ONGOING'")
    private DeliveryType deliveryStatus;

    /* 라이더 프로필사진 */
    @NotNull private String filePath;
    @NotNull private String fileUuid;
    @NotNull private String fileOrgName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Welfare welfare;

    public Rider(String riderName, String riderPhoneNumber, String filePath, String fileUuid, String fileOrgName, Welfare welfare) {
        this.riderName = riderName;
        this.riderPhoneNumber = riderPhoneNumber;
        this.filePath = filePath;
        this.fileUuid = fileUuid;
        this.fileOrgName = fileOrgName;
        this.welfare = welfare;
    }
}
