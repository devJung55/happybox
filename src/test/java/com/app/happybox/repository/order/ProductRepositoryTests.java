package com.app.happybox.repository.order;

import com.app.happybox.entity.order.Product;
import com.app.happybox.entity.order.Product;
import com.app.happybox.entity.order.ProductSearch;
import com.app.happybox.entity.type.ProductSearchOrder;
import com.app.happybox.entity.user.Distributor;
import com.app.happybox.repository.user.DistributorRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional @Rollback(false)
@Slf4j
class ProductRepositoryTests {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DistributorRepository distributorRepository;

    @Test
    public void saveTest() {
        // given
        distributorRepository.findById(8L).ifPresent(distributor -> {
            Product product = new Product("정표사과", 1_800, distributor);
            product.setProductStock(5_000L);
            productRepository.save(product);
        });

        // when

        // then
    }

    @Test
    public void findTop8WithDistributorAndReviewCountOrderByDate_QueryDSL_Test() {
        // given
        productRepository
                .findTop8WithDistributorAndReviewCountOrderByDate_QueryDSL()
                .stream()
                .map(Product::toString)
                .forEach(log::info);
        // when

        // then
    }

    @Test
    public void findByIdWithDetail_QueryDSL_Test() {
        // given
        Optional<Product> product = productRepository.findByIdWithDetail_QueryDSL(9L);

        // when

        // then
        product.map(Product::toString).ifPresent(log::info);
    }

    @Test
    public void findAllByProductSearch_QueryDSL(){
        // given
        ProductSearch productSearch = new ProductSearch();

        productSearch.setAddress("경남");
        productSearch.setName("사과");
        productSearch.setProductSearchOrder(ProductSearchOrder.PRICE_DESC);

        // when
        Page<Product> products = productRepository.findAllByProductSearch_QueryDSL(PageRequest.of(0, 10), productSearch);

        // then
        products.get().map(Product::toString).forEach(log::info);
    }
}