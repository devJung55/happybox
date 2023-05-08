package com.app.happybox.repository.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional @Rollback(false)
@Slf4j
public class MemberOrderProductItemRepositoryTests {
    @Autowired private MemberOrderProductItemRepository memberOrderProductItemRepository;

    @Test
    public void findOrderListByMemberIdDescWithPagingTest() {
        memberOrderProductItemRepository.findOrderListByMemberIdDescWithPaging_QueryDSL(PageRequest.of(0, 5), 1L)
                .stream().forEach(v -> {
                    log.info(v.getProduct().getProductName());
        });
    }

    @Test
    public void findCancleListByMemberIdDescWithPagingTest() {
        memberOrderProductItemRepository.findCancleListByMemberIdDescWithPaging_QueryDSL(PageRequest.of(0, 5), 1L)
                .stream().forEach(v -> {
                    log.info(v.getProduct().getProductName());
        });
    }
}