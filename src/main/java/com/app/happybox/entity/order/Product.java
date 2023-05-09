package com.app.happybox.entity.order;

import com.app.happybox.audity.Period;
import com.app.happybox.entity.file.ProductFile;
import com.app.happybox.entity.reply.ProductReply;
import com.app.happybox.entity.user.Distributor;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "TBL_PRODUCT")
@DynamicInsert
@Getter @ToString(exclude = {"productReplies", "productFiles"}) @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends Period {
    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;

    /* ===== 상품 기본 정보 ===== */
    @NotNull
    private String productName;
    @NotNull
    private Integer productPrice;
    @ColumnDefault(value = "0")
    private Long productStock;
    @NotNull
    private String productInfo;

    /* ---- 반정규화 ---- */
    @ColumnDefault(value = "0")
    private Integer productLikeCount;

    /* ======================= */

    /* 유통회원 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Distributor distributor;

    /* 상품 댓글 */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProductReply> productReplies = new ArrayList<>();

    /* 상품 파일 */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<ProductFile> productFiles = new ArrayList<>();

    public Product(String productName, Integer productPrice, Distributor distributor) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.distributor = distributor;
    }

    public Product(String productName, Integer productPrice, String productInfo, List<ProductFile> productFiles, Distributor distributor) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productInfo = productInfo;
        this.productFiles = productFiles;
        this.distributor = distributor;
    }

    public void setProductStock(Long productStock) {
        this.productStock = productStock;
    }
}
