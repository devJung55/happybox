package com.app.happybox.repository.user;

import com.app.happybox.entity.user.Address;
import com.app.happybox.entity.user.Welfare;
import com.app.happybox.repository.subscript.SubscriptionRepository;
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
class WelfareRepositoryTests {
    @Autowired
    private WelfareRepository welfareRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Test
    public void saveTest() {
        // given
        Welfare welfare = new Welfare("hds1234",
                "1234",
                new Address("11111", "서울시 역삼동", "코리아IT"),
                "hds1234@gmail.com",
                "01012341234",
                "강남노인복지관");

        // when
        welfareRepository.save(welfare);

        // then
    }

    @Test
    public void findByIdTest(){
        // given
        welfareRepository.findById(2L).map(Welfare::toString).ifPresent(log::info);

        // when

        // then
    }
}