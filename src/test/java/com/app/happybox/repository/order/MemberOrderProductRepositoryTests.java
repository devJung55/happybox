package com.app.happybox.repository.order;

import com.app.happybox.entity.order.MemberOrderProduct;
import com.app.happybox.entity.order.MemberOrderProductItem;
import com.app.happybox.entity.product.Product;
import com.app.happybox.entity.user.Member;
import com.app.happybox.exception.NotEnoughStockException;
import com.app.happybox.repository.product.ProductRepository;
import com.app.happybox.repository.user.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional @Rollback(false)
@Slf4j
class MemberOrderProductRepositoryTests {
    @Autowired
    private MemberOrderProductRepository memberOrderProductRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

//    @Test
//    public void saveTest() {
//        // given
//        Optional<Member> member = memberRepository.findById(1L);
//        Optional<Product> product = productRepository.findById(9L);
//
//        // when
//        if (!member.isPresent() || !product.isPresent()) fail("member 혹은 product 존재 하지 않음");
//
//        MemberOrderProductItem item = new MemberOrderProductItem(300L, product.get());
//        MemberOrderProduct memberOrderProduct = new MemberOrderProduct(
//                member.get().getAddress(),
//                member.get()
//        );
//
//        List<MemberOrderProductItem> items = new ArrayList<>(Arrays.asList(item));
//
//        memberOrderProduct.addProducts(items);
//        items.forEach(i -> {
//            Product itemProduct = i.getProduct();
//            Long productStock = itemProduct.getProductStock();
//
//            if(productStock - i.getOrderAmount() < 0) {
//                throw new NotEnoughStockException("상품 수량이 부족합니다.");
//            }
//
//            itemProduct.setProductStock(productStock - i.getOrderAmount());
//        });
//
//        memberOrderProductRepository.save(memberOrderProduct);
//
//        // then
//        log.info(memberOrderProduct.toString());
//    }

    @Test
    public void findOrderCountByMemberIdAndOrderStatus_QueryDSL_Test() {
        log.info("orderCount : " + memberOrderProductRepository.findOrderCountByMemberIdAndOrderStatus_QueryDSL(1L).toString());
    }
}