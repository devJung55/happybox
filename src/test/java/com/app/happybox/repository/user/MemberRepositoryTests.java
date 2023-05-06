package com.app.happybox.repository.user;

import com.app.happybox.entity.type.Gender;
import com.app.happybox.entity.user.Address;
import com.app.happybox.entity.user.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional @Rollback(false)
@Slf4j
class MemberRepositoryTests {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void saveTest(){
        // given
        Address address = new Address("11111", "서울시 역삼동", "코리아IT");
        Member member = new Member("kmg1234",
                "1234",
                address,
                "kmg1234@gmail.com",
                "01034442331",
                "강민구",
                LocalDate.now(),
                Gender.MALE,
                address);

        // when

        // then
        memberRepository.save(member);
    }

    @Test
    public void findByIdTest(){
        // given
        memberRepository.findById(1L).map(Member::toString).ifPresent(log::info);

        // when

        // then
    }


}