package com.app.happybox.domain.product;

import com.app.happybox.entity.file.ProductFile;
import com.app.happybox.entity.file.ProductFileDTO;
import com.app.happybox.entity.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter @ToString
public class ProductCartDTO {
    private Long id;

    // 구매 수량
    private Long cartOrderAmount;

    // 상품 이름
    private String productName;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private ProductDTO productDTO;

    private ProductFileDTO productFileDTO;

    @Builder
    public ProductCartDTO(Long id, Long cartOrderAmount, String productName, LocalDateTime createdDate, LocalDateTime updatedDate, ProductDTO productDTO,ProductFileDTO productFileDTO) {
        this.id = id;
        this.cartOrderAmount = cartOrderAmount;
        this.productName = productName;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.productDTO = productDTO;
        this.productFileDTO = productFileDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    public void setProductFileDTO(ProductFileDTO productFileDTO) {
        this.productFileDTO = productFileDTO;
    }
}
