package com.app.happybox.service.product;

import com.app.happybox.domain.product.ProductDTO;
import com.app.happybox.entity.file.ProductFile;
import com.app.happybox.entity.file.ProductFileDTO;
import com.app.happybox.entity.product.Product;
import com.app.happybox.entity.product.ProductCart;
import com.app.happybox.domain.product.ProductCartDTO;
import com.app.happybox.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface ProductCartService {

    public List<ProductCartDTO> findAllByUserId(Long id);

    public Long saveCart(ProductCartDTO cartDTO, Long userId, Long productId);

    //    CartId로 Product 조회
    public ProductDTO findProductById(Long id);

    //    Product 아이디로 ProductFile 조회

    default ProductCartDTO cartToDTO(ProductCart productCart) {
        return ProductCartDTO.builder()
                .id(productCart.getId())
                .cartOrderAmount(productCart.getCartOrderAmount())
                .productName(productCart.getProduct().getProductName())
                .build();
    }

    default ProductCart productCartDTOToEntity(ProductCartDTO productCartDTO, User user, Product product) {
        return ProductCart.builder()
                .user(user)
                .cartOrderAmount(productCartDTO.getCartOrderAmount())
                .product(product)
                .build();
    }

    default ProductDTO productToProductDTO(Product product){
        return ProductDTO.builder()
                .productCategory(product.getProductCategory())
                .productPrice(product.getProductPrice())
                .productName(product.getProductName())
                .id(product.getId())
                .productStock(product.getProductStock())
                .build();
    }

    default ProductFileDTO productFileToProductFileDTO(ProductFile productFile){
        return ProductFileDTO.builder()
                .fileOrgName(productFile.getFileOrgName())
                .filePath(productFile.getFilePath())
                .fileRepresent(productFile.getFileRepresent())
                .fileUuid(productFile.getFileUuid())
                .id(productFile.getId())
                .build();
    }

}
