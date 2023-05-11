package com.app.happybox.repository.user;

import com.app.happybox.type.Gender;
import com.app.happybox.type.Role;
import com.app.happybox.type.UserStatus;
import com.app.happybox.entity.user.Address;
import com.app.happybox.entity.user.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.time.LocalDate;

@SpringBootTest
@Transactional @Rollback(false)
@Slf4j
class MemberRepositoryTests {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void saveTest(){
//        // given
        Address address = new Address("11111", "서울시 역삼동", "코리아IT");
        Member member = Member.builder()
                .memberBirth(LocalDate.of(2000,06,01))
                .address(address)
                .memberDeliveryAddress(address)
                .memberGender(Gender.MALE)
                .memberName("오태양")
                .deliveryName("강민구")
                .deliveryPhoneNumber("010-6484-4195")
                .userEmail("oty7942@gmail.com")
                .userId("oty7942")
                .userPassword("asd1234")
                .userPhoneNumber("010-6484-4195")
                .userRole(Role.MEMBER)
                .userStatus(UserStatus.REGISTERED)
                .build();
        memberRepository.save(member);
    }

    @Test
    public void findByIdTest(){
        // given
        memberRepository.findById(1L).map(Member::toString).ifPresent(log::info);

        // when

        // then
    }

//    로그인 테스트
    @Test
    public void loginTest(){
    }

//    Phone으로 회원 정보 확인
    @Test
    public void findMemberByMemberPhoneTest(){
//        memberRepository.findMemberByMemberPhone("01034442331").ifPresent(member -> log.info(member.toString()));
    }

//    ID 중복체크
    @Test
    public void checkIdTest(){
//        log.info(memberRepository.checkId("1234").toString());
    }

//  마이페이지 배송지정보
    @Test
    public void findDeliveryAddressByMemberIdTest() {
        memberRepository.findDeliveryAddressByMemberId_QueryDSL(1L)
                .ifPresent(member -> log.info(member.toString()));
    }
}