package com.app.happybox.service.product;

import com.app.happybox.entity.file.BoardFile;
import com.app.happybox.entity.file.BoardFileDTO;
import com.app.happybox.entity.file.ProductFile;
import com.app.happybox.entity.file.ProductFileDTO;
import com.app.happybox.entity.product.Product;
import com.app.happybox.domain.product.ProductDTO;
import com.app.happybox.domain.product.ProductSearchDTO;
import com.app.happybox.entity.user.Distributor;
import com.app.happybox.exception.ProductNotFoundException;
import com.app.happybox.exception.UserNotFoundException;
import com.app.happybox.repository.product.ProductFileRepository;
import com.app.happybox.repository.product.ProductRepository;
import com.app.happybox.repository.user.DistributorRepository;
import com.app.happybox.type.FileRepresent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Qualifier("product")
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final DistributorRepository distributorRepository;
    private final ProductFileRepository productFileRepository;

    @Override
    public List<ProductDTO> findTop8Recent() {
        return productRepository.findTop8WithDistributorAndReviewCountOrderByDate_QueryDSL()
                .stream()
                .map(this::productToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> findRandomProducts() {
        return productRepository.findRandomProducts_QueryDSL()
                .stream()
                .map(this::productToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> findTop8ReplyCount() {
        return productRepository.findTop8WithDetailOrderByReplyCount_QueryDSL()
                .stream()
                .map(this::productToDTO)
                .collect(Collectors.toList());
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



    @Override @Transactional
    public void saveProduct(Long distributorId, ProductDTO productDTO) {
        List<ProductFileDTO> productFileDTOS = productDTO.getProductFileDTOS();

        // 유통업자 찾기
        Distributor distributor = distributorRepository.findById(distributorId).orElseThrow(UserNotFoundException::new);
        // 상품 엔티티로 변환
        Product product = productToEntity(productDTO);
        product.setDistributor(distributor);

        productRepository.save(product);

        int index = 0;

        // boardFile recipeBoard set 후 영속화
        for (int i = 0; i < productFileDTOS.size(); i++) {
            ProductFile productFile = productFileToEntity(productFileDTOS.get(i));
            if(index < 1) productFile.setFileRepresent(FileRepresent.REPRESENT);

            productFile.setProduct(product);
            productFileRepository.save(productFile);

            index++;
        }

    }

    @Override
    public Page<ProductDTO> getListByDistributorId(Pageable pageable, Long distributorId) {
        Page<Product> productList = productRepository.findAllByDistributorIdWithPaging_QueryDSL(pageable, distributorId);
        List<ProductDTO> productDTOList = productList.get().map(this::adminProductToDTO).collect(Collectors.toList());

        return new PageImpl<>(productDTOList, pageable, productList.getTotalElements());
    }

    @Override
    public Optional<Product> getDetailById(Long productId) {
        return Optional.ofNullable(productRepository.findById(productId).get());
    }

    @Override
    public Long getProductCount(Long distributorId) {
        Long productCount = productRepository.findCountByDistributor_QueryDSL(distributorId);
        return productCount;
    }
}
