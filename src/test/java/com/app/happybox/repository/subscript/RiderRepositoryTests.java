package com.app.happybox.repository.subscript;

import com.app.happybox.entity.subscript.Rider;
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
public class RiderRepositoryTests {
    @Autowired private RiderRepository riderRepository;

    @Test
    public void findAllByWelfareIdWithPaging_QueryDSL_Test() {
        riderRepository.findAllByWelfareIdWithPaging_QueryDSL(PageRequest.of(0, 5), 42L)
                .stream().map(Rider::toString).forEach(log::info);
    }
}
