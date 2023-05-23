package com.app.happybox.domain.product;

import com.app.happybox.domain.AddressDTO;
import com.app.happybox.domain.user.DistributorDTO;
import com.app.happybox.entity.file.ProductFileDTO;
import com.app.happybox.entity.user.Address;
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

    private String productInfo;

    private Integer productPrice;

    private Long productStock;

    private Integer productReplyCount;

    private ProductCategory productCategory;

    private Integer productLikeCount;

    private Long productOrderCount;
    /* ======================== */

    private DistributorDTO distributorDTO;

    private List<ProductFileDTO> productFileDTOS = new ArrayList<>();

//    유통업자 이름
    private String distributorName;

//    유통업자 주소 (원산지)
    private AddressDTO address;

    @Builder
    public ProductDTO(Long id, String productName, String productInfo, Integer productPrice, Long productStock,
                      Integer productReplyCount, ProductCategory productCategory, Integer productLikeCount,
                      Long productOrderCount, List<ProductFileDTO> productFileDTOS, DistributorDTO distributorDTO, String distributorName, AddressDTO address) {
        this.id = id;
        this.productName = productName;
        this.productInfo = productInfo;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productReplyCount = productReplyCount;
        this.productCategory = productCategory;
        this.productLikeCount = productLikeCount;
        this.productOrderCount = productOrderCount;
        this.productFileDTOS = productFileDTOS;
        this.distributorDTO = distributorDTO;
        this.distributorName = distributorName;
        this.address = address;
    }
}
