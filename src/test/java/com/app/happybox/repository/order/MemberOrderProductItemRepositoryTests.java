package com.app.happybox.repository.order;

import com.app.happybox.audity.Period;
import com.app.happybox.entity.order.MemberOrderProductItem;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional @Rollback(false)
@Slf4j
public class MemberOrderProductItemRepositoryTests {
    @Autowired private MemberOrderProductItemRepository memberOrderProductItemRepository;

    @Test
    public void findCancleListByMemberIdDescWithPagingTest() {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.of(2023, 4, 8, 0, 0);

        memberOrderProductItemRepository
                .findCancleListByMemberIdAndSearchDateDescWithPaging_QueryDSL(PageRequest.of(0, 5), 1L, startDate, endDate)
                .stream().map(MemberOrderProductItem::getProduct).forEach(v -> log.info(v.getProductFiles().toString()));
    }
}