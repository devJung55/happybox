package com.app.happybox.entity.product;

import com.app.happybox.audity.Period;
import com.app.happybox.entity.file.ProductFile;
import com.app.happybox.entity.reply.ProductReply;
import com.app.happybox.type.ProductCategory;
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
    @Column(columnDefinition = "CLOB")
    private String productInfo;

    @Enumerated(EnumType.STRING)
    @ColumnDefault(value = "'OTHER'")
    private ProductCategory productCategory;

    /* ---- 반정규화 ---- */

    @ColumnDefault(value = "0")
    private Integer productLikeCount;

    @ColumnDefault(value = "0")
    private Integer productReplyCount;

//    주문 횟수
    @ColumnDefault(value = "0")
    private Long productOrderCount;

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

    @Builder
    public Product(String productName, Integer productPrice, Long productStock, String productInfo, ProductCategory productCategory, List<ProductFile> productFiles) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productInfo = productInfo;
        this.productCategory = productCategory;
        this.productFiles = productFiles;
    }

    public void setProductStock(Long productStock) {
        this.productStock = productStock;
    }

    public void setProductOrderCount(Long productOrderCount) {
        this.productOrderCount = productOrderCount;
    }

    public void setProductReplyCount(Integer productReplyCount) {
        this.productReplyCount = productReplyCount;
    }

    public void setDistributor(Distributor distributor) {
        this.distributor = distributor;
    }
}
