package com.app.happybox.repository.user;

import com.app.happybox.entity.user.Member;
import com.querydsl.core.Tuple;

import java.util.Optional;

public interface MemberQueryDsl {
    //    회원정보수정
    public void setMemberInfoById_QueryDSL(Member member);

    //    회원탈퇴
    public void setMemberStatusById_QueryDSL(Member member);

    //    로그인을 위해 ID로 Identification, Password 조회
    public Tuple findMemberInfoById(Long id);

    //    가입여부 확인
    public Optional<Member> findMemberByMemberPhone(String MemberPhone);

    //    아이디 중복체크
    public Boolean checkId(String memberId);

//    마이페이지 배송지정보
    public Optional<Member> findDeliveryAddressByMemberId_QueryDSL(Member member);
}


