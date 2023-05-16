package com.app.happybox.domain;

import com.app.happybox.entity.file.ProductFileDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter @ToString
public class MemberOrderProductItemDTO {
    private Long id;
    private String productName;
    private Integer productPrice;
    private Long orderStock;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private List<ProductFileDTO> productFiles;

    @Builder
    public MemberOrderProductItemDTO(Long id, String productName, Integer productPrice, Long orderStock, LocalDateTime createdDate, LocalDateTime updatedDate, List<ProductFileDTO> productFiles) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.orderStock = orderStock;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.productFiles = productFiles;
    }
}
