package com.app.happybox.repository.user;

import com.app.happybox.entity.user.Member;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MemberQueryDsl {
    //    회원정보수정
    public void setMemberInfoById_QueryDSL(Member member);

    //    member 로그인
    public Optional<Member> logIn(String memberId, String memberPassword);

//    아이디 찾기
    public Optional<String> findMemberIdByPhoneNumber(String memberPhoneNumber);

//    아이디 찾기(memberEmail)
    public Optional<String> findMemberIdByEmail(String memberEmail);

//    마이페이지 배송지정보
    public Optional<Member> findDeliveryAddressByMemberId_QueryDSL(Member member);

//    관리자 회원 목록
    public Page<Member> findAllWithPaging_QueryDSL(Pageable pageable);
}


