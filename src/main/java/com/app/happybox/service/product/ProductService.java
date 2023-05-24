package com.app.happybox.service.product;

import com.app.happybox.domain.AddressDTO;
import com.app.happybox.entity.file.BoardFile;
import com.app.happybox.entity.file.BoardFileDTO;
import com.app.happybox.entity.file.ProductFile;
import com.app.happybox.entity.product.Product;
import com.app.happybox.domain.product.ProductDTO;
import com.app.happybox.entity.file.ProductFileDTO;
import com.app.happybox.domain.product.ProductSearchDTO;
import com.app.happybox.entity.user.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface ProductService {

//    최신 8개 조회
    public List<ProductDTO> findTop8Recent();

//    랜덤 2개 조회
    public List<ProductDTO> findRandomProducts();

//    추천으로 조회
    public List<ProductDTO> findTop8ReplyCount();

//    상품 동적검색
    public Page<ProductDTO> findAllBySearch(Pageable pageable, ProductSearchDTO search);

//    상품 상세조회
    public ProductDTO findById(Long id);

//    상품 등록
    public void saveProduct(Long distributorId, ProductDTO productDTO);

//    관리자 해당 유통회원의 상품 목록
    public Page<ProductDTO> getListByDistributorId(Pageable pageable, Long distributorId);

//    관리자 상품 상세보기
    public Optional<Product> getDetailById(Long productId);

//    마이페이지 상품 개수 조회
    public Long getProductCount(Long distributorId);

    default Product productToEntity(ProductDTO productDTO) {
        return Product.builder()
                .productName(productDTO.getProductName())
                .productInfo(productDTO.getProductInfo())
                .productPrice(productDTO.getProductPrice())
                .productCategory(productDTO.getProductCategory())
                .productStock(productDTO.getProductStock())
                .build();
    }

    default ProductDTO productToDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .distributorName(product.getDistributor().getDistributorName())
                .productCategory(product.getProductCategory())
                .productLikeCount(product.getProductLikeCount())
                .productName(product.getProductName())
                .productOrderCount(product.getProductOrderCount())
                .productPrice(product.getProductPrice())
                .productInfo(product.getProductInfo())
                .productReplyCount(product.getProductReplyCount())
                .productStock(product.getProductStock())
                .productFileDTOS(productFileListToDTO(product.getProductFiles()))
                .address(this.addressToDTO(product.getDistributor().getAddress()))
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

    default List<ProductFileDTO> productFileListToDTO(List<ProductFile> productFiles) {
        List<ProductFileDTO> productFileDTOS = new ArrayList<>();
        productFiles.forEach(productFile -> {
            ProductFileDTO productFileDTO = ProductFileDTO.builder()
                    .id(productFile.getId())
                    .filePath(productFile.getFilePath())
                    .fileUuid(productFile.getFileUuid())
                    .fileOrgName(productFile.getFileOrgName())
                    .fileRepresent(productFile.getFileRepresent())
                    .build();
                productFileDTOS.add(productFileDTO);
        });

        return productFileDTOS;
    }

    default ProductFile productFileToEntity(ProductFileDTO productFileDTO) {
        return ProductFile.builder()
                .filePath(productFileDTO.getFilePath())
                .fileUuid(productFileDTO.getFileUuid())
                .fileOrgName(productFileDTO.getFileOrgName())
                .build();
    }

    default ProductDTO adminProductToDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .productCategory(product.getProductCategory())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .productStock(product.getProductStock())
                .productFileDTOS(
                        product.getProductFiles().stream()
                                .map(this::productFileToDTO)
                                .collect(Collectors.toList())
                )
                .build();
    }

    default AddressDTO addressToDTO(Address address) {
        return AddressDTO.builder()
                .firstAddress(address.getFirstAddress())
                .addressDetail(address.getAddressDetail())
                .zipcode(address.getZipcode())
                .build();
    }
}
