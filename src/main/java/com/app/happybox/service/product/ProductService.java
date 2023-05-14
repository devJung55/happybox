package com.app.happybox.service.product;

import com.app.happybox.entity.file.ProductFile;
import com.app.happybox.entity.product.Product;
import com.app.happybox.entity.product.ProductDTO;
import com.app.happybox.entity.file.ProductFileDTO;
import com.app.happybox.entity.product.ProductSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

public interface ProductService {

//    최신 8개 조회
    public List<ProductDTO> findTop8Recent();

//    추천으로 조회
    public List<ProductDTO> temp();

//    상품 동적검색
    public Page<ProductDTO> findAllBySearch(Pageable pageable, ProductSearchDTO search);

//    상품 상세조회
    public ProductDTO findById(Long id);

    default ProductDTO productToDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .distributorName(product.getDistributor().getDistributorName())
                .productCategory(product.getProductCategory())
                .productLikeCount(product.getProductLikeCount())
                .productName(product.getProductName())
                .productOrderCount(product.getProductOrderCount())
                .productPrice(product.getProductPrice())
                .productReplyCount(product.getProductReplyCount())
                .productStock(product.getProductStock())
                .productFileDTOS(
                        product.getProductFiles().stream()
                                .map(this::productFileToDTO)
                                .collect(Collectors.toList())
                )
                .build();
    }

    default ProductFileDTO productFileToDTO(ProductFile file) {
        return ProductFileDTO.builder()
                .fileOrgName(file.getFileOrgName())
                .filePath(file.getFilePath())
                .fileRepresent(file.getFileRepresent())
                .fileUuid(file.getFileUuid())
                .id(file.getId())
                .build();
    }
}
