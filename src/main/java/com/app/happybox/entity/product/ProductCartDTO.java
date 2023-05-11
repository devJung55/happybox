package com.app.happybox.entity.product;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @ToString
public class ProductCartDTO {
    private Long id;

    // 구매 수량
    private Long cartOrderAmount;

    // 상품 이름
    private String productName;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    @Builder
    public ProductCartDTO(Long id, Long cartOrderAmount, String productName, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.cartOrderAmount = cartOrderAmount;
        this.productName = productName;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
