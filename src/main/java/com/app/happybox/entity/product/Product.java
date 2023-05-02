package com.app.happybox.entity.product;

import com.app.happybox.audity.Period;
import com.app.happybox.entity.user.Distributor;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity @Table(name = "TBL_PRODUCT")
@Getter @ToString(exclude = "distributor") @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends Period {
    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;

    /* ===== 상품 기본 정보 ===== */
    @NotNull
    private String productName;
    @NotNull
    private Integer productPrice;
    /* ======================= */

    /* 유통회원 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Distributor distributor;
}
