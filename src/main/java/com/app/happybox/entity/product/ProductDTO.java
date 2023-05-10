package com.app.happybox.entity.product;

import com.app.happybox.entity.file.ProductFileDTO;
import com.app.happybox.type.ProductCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter @ToString
public class ProductDTO {
    private Long id;

    /* ===== 상품 기본 정보 ===== */
    private String productName;

    private Integer productPrice;

    private Long productStock;

    private Integer productReplyCount;

    private ProductCategory productCategory;

    private Integer productLikeCount;

    private Long productOrderCount;
    /* ======================== */

    private List<ProductFileDTO> productFileDTOS = new ArrayList<>();

//    유통업자 이름
    private String distributorName;

    @Builder
    public ProductDTO(Long id, String productName, Integer productPrice, Long productStock,
                      Integer productReplyCount, ProductCategory productCategory, Integer productLikeCount,
                      Long productOrderCount, List<ProductFileDTO> productFileDTOS, String distributorName) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productReplyCount = productReplyCount;
        this.productCategory = productCategory;
        this.productLikeCount = productLikeCount;
        this.productOrderCount = productOrderCount;
        this.productFileDTOS = productFileDTOS;
        this.distributorName = distributorName;
    }
}
