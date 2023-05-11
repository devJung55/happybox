package com.app.happybox.repository.reply;

import com.app.happybox.entity.product.Product;
import com.app.happybox.entity.reply.ProductReply;
import com.app.happybox.entity.user.Member;
import com.app.happybox.repository.product.ProductRepository;
import com.app.happybox.repository.user.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional @Rollback(false)
@Slf4j
class ProductReplyRepositoryTests {
    @Autowired
    private ProductReplyRepository productReplyRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void saveTest() {
        // given
        Optional<Member> member = memberRepository.findById(1L);
        Optional<Product> product = productRepository.findById(9L);

        // when
        if (!member.isPresent() || !product.isPresent()) fail("member 혹은 product 없음.");

        ProductReply productReply = new ProductReply(
                "주인장이 맛있고 사과가 친절해요.",
                member.get(),
                product.get()
        );

        productReplyRepository.save(productReply);

        // then
        log.info(productReply.toString());
    }

    @Test
    public void findAllByProductIdTest() {
        // given
        Long id = 1L;

        // when
        productReplyRepository.findAllByProductId(PageRequest.of(0, 10), id);

        // then
    }
}