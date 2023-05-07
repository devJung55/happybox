package com.app.happybox.entity.order;

import com.querydsl.core.annotations.QueryProjection;
import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter @ToString
public class ProductDTO {
    private Long id;

    /* ===== 상품 기본 정보 ===== */
    private String productName;

    private Integer productPrice;

    private Long productStock;
    /* ======================== */

//    유통업자 이름
    private String distributorName;

//    댓글수
    private Integer replyCount;

    @QueryProjection
    public ProductDTO(Long id, String productName, Integer productPrice, Long productStock, String distributorName, Integer replyCount) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.distributorName = distributorName;
        this.replyCount = replyCount;
    }
}
