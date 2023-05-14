package com.app.happybox.service.product;

import com.app.happybox.entity.product.Product;
import com.app.happybox.entity.product.ProductDTO;
import com.app.happybox.entity.product.ProductSearchDTO;
import com.app.happybox.exception.ProductNotFoundException;
import com.app.happybox.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Qualifier("product")
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<ProductDTO> findTop8Recent() {
        return productRepository.findTop8WithDistributorAndReviewCountOrderByDate_QueryDSL()
                .stream()
                .map(this::productToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> temp() {

        return null;
    }

    @Override
    public Page<ProductDTO> findAllBySearch(Pageable pageable, ProductSearchDTO search) {
        Page<Product> productPage = productRepository.findAllByProductSearch_QueryDSL(pageable, search);
        List<ProductDTO> productDTOList = productPage.get().map(this::productToDTO).collect(Collectors.toList());
        return new PageImpl<>(productDTOList, pageable, productPage.getTotalElements());
    }

    @Override
    public ProductDTO findById(Long id) {
        Product product = productRepository.findByIdWithDetail_QueryDSL(id).orElseThrow(() -> {
            throw new ProductNotFoundException();
        });

        return productToDTO(product);
    }
}
