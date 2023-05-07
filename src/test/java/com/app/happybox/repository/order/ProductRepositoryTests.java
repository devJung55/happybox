package com.app.happybox.repository.order;

import com.app.happybox.entity.order.Product;
import com.app.happybox.entity.order.ProductDTO;
import com.app.happybox.entity.user.Distributor;
import com.app.happybox.repository.user.DistributorRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

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
    public void saveTest(){
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
    public void findTop8WithDistributorAndReviewCountOrderByDate_QueryDSL_Test(){
        // given
        productRepository
                .findTop8WithDistributorAndReviewCountOrderByDate_QueryDSL()
                .stream()
                .map(ProductDTO::toString)
                .forEach(log::info);
        // when

        // then
    }
}