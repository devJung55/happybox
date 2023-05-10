package com.app.happybox.repository.order;

import com.app.happybox.entity.product.Cart;
import com.app.happybox.repository.user.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional @Rollback(false)
@Slf4j
class CartRepositoryTests {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void saveTest(){
        memberRepository.findById(1L).ifPresent(member -> {
            productRepository.findById(9L).ifPresent(product -> {
                Cart cart = new Cart(30L, product, member);
                cartRepository.save(cart);
            });
        });
    }

    @Test
    public void deleteByUser_UserIdTest(){
        cartRepository.deleteByUserId_QueryDSL(1L);
    }
}