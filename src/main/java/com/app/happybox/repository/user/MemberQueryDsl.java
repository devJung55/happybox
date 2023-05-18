package com.app.happybox.repository.user;

import com.app.happybox.entity.user.Member;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public interface MemberQueryDsl {
    //    회원정보수정
    public void setMemberInfoById_QueryDSL(PasswordEncoder passwordEncoder, Member member);

    //    member 로그인
    public Optional<Member> logIn(String memberId, String memberPassword);

//    아이디 찾기
    public Optional<String> findMemberIdByPhoneNumber(String userPhoneNumber);

//    아이디 찾기(memberEmail)
    public Optional<String> findMemberIdByEmail(String memberEmail);

//    비밀번호 찾기
    public Optional<String> findMemberPwByPhoneNumber(String memberPhoneNumber);

    //    마이페이지 배송지정보
    public Optional<Member> findDeliveryAddressByMemberId_QueryDSL(Long memberId);

    //    관리자 회원 목록
    public Page<Member> findAllWithPaging_QueryDSL(Pageable pageable);

//    관리자 회원 조회
    public Optional<Member> findMemberById_QueryDSL(Long memberId);

//    배송지정보수정
    public void setMemberDeliveryAddressByMemberId(Member member);
}


