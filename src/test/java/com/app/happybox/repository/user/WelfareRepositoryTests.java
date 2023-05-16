//package com.app.happybox.repository.user;
//
//import com.app.happybox.entity.user.Address;
//import com.app.happybox.entity.user.Welfare;
//import com.app.happybox.repository.subscript.SubscriptionRepository;
//import com.app.happybox.type.Role;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//
//import javax.transaction.Transactional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional @Rollback(false)
//@Slf4j
//class WelfareRepositoryTests {
//    @Autowired
//    private WelfareRepository welfareRepository;
//    @Autowired
//    private SubscriptionRepository subscriptionRepository;
//
//
//
//    @Test
//    public void saveTest() {
//        for (int i = 0; i < 100; i++) {
//
//        }
//        Address address = new Address("11111", "서울시 역삼동", "코리아IT");
//        Welfare welfare = new Welfare("kmg123",
//                "asd1234",
//                address,
//                "kmg123@gmail.com",
//                "01012341234",
//                Role.WELFARE,
//                "민구복지관",
//                1000);
//        welfareRepository.save(welfare);
//    }
//
//    @Test
//    public void findByIdTest(){
//        // given
//        welfareRepository.findById(2L).map(Welfare::toString).ifPresent(log::info);
//
//        // when
//
//        // then
//    }
//
//    @Test
//    public void loginTest(){
////        log.info(welfareRepository.findWelfareInfoById(2L).toString());
//    }
//
//}