package com.app.happybox.entity.subscript;

import com.app.happybox.audity.Period;
import com.app.happybox.entity.user.Welfare;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @ToString
@Table(name = "TBL_RIDER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Rider extends Period {
    @Id @GeneratedValue @EqualsAndHashCode.Include
    private Long id;
    @NotNull private String riderName;
    @Column(unique = true)
    private String riderPhoneNumber;

    /* 라이더 프로필사진 */
    @NotNull private String filePath;
    @NotNull private String fileUuid;
    @NotNull private String fileOrgName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Welfare welfare;
}
