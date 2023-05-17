package com.app.happybox.repository.product;

import com.app.happybox.entity.product.ProductCart;
import com.app.happybox.repository.user.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Transactional @Rollback(false)
@Slf4j
class ProductCartRepositoryTests {
    @Autowired
    private ProductCartRepository cartRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void saveTest() {
        memberRepository.findById(2L).ifPresent(member -> {
            productRepository.findById(8L).ifPresent(product -> {
                ProductCart productCart = new ProductCart(30L, product, member);
                cartRepository.save(productCart);
            });
        });
    }

    @Test
    public void deleteByUser_UserIdTest() {
        cartRepository.deleteByUserId_QueryDSL(1L);
    }

    @Test
    public void findAllByUserId_QueryDSL_Test() {
        List<ProductCart> productCartList = cartRepository.findAllByUserId_QueryDSL(1L);

        productCartList.stream().map(ProductCart::toString).forEach(log::info);
    }

    @Test
    public void findAllByIdsWithDetail_QueryDSL_Test() {
        cartRepository.findAllByIdsWithDetail_QueryDSL(Arrays.asList(1L, 2L))
                .stream()
                .map(ProductCart::toString)
                .forEach(log::info);
    }
}