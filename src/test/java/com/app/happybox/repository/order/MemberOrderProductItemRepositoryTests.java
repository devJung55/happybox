package com.app.happybox.repository.order;

import com.app.happybox.entity.order.MemberOrderProductItem;
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
        memberOrderProductItemRepository.findOrderListByMemberIdDescWithPaging_QueryDSL(PageRequest.of(1, 5), 1L)
                .stream().map(MemberOrderProductItem::getProduct).forEach(v -> log.info(v.getProductName()));
    }
}
