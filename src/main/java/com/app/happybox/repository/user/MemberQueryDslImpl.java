package com.app.happybox.repository.user;

import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.QMember;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.app.happybox.entity.user.QMember.member;

@RequiredArgsConstructor
public class MemberQueryDslImpl implements MemberQueryDsl {
    private final JPAQueryFactory query;

    //    회원정보수정
    @Override
    public void setMemberInfoById_QueryDSL(Member member) {
        query.update(QMember.member)
                .set(QMember.member.userPassword, member.getUserPassword())
                .set(QMember.member.memberName, member.getMemberName())
                .set(QMember.member.userPhoneNumber, member.getUserPhoneNumber())
                .set(QMember.member.userEmail, member.getUserEmail())
                .set(QMember.member.memberBirth, member.getMemberBirth())
                .set(QMember.member.memberGender, member.getMemberGender())
                .where(QMember.member.eq(member))
                .execute();
    }

    /* member Login */
    @Override
    public Optional<Member> logIn(String memberId, String memberPassword) {
        return Optional.ofNullable(query.select(QMember.member)
                .from(QMember.member)
                .where(QMember.member.userId.eq(memberId).and(QMember.member.userPassword.eq(memberPassword)))
                .fetchOne());
    }

//    아이디 찾기(memberPhone)
    @Override
    public Optional<String> findMemberIdByPhoneNumber(String memberPhoneNumber) {
        return Optional.ofNullable(query.select(member.userId)
                .from(member)
                .where(member.userPhoneNumber.eq(memberPhoneNumber))
                .fetchOne());
    }


    //    아이디 찾기(memberEmail)
    @Override
    public Optional<String> findMemberIdByEmail(String memberEmail) {
        return Optional.ofNullable(query.select(member.userId)
        .from(member)
        .where(member.userEmail.eq(memberEmail))
        .fetchOne());
    }


    //    마이페이지 배송지정보
    @Override
    public Optional<Member> findDeliveryAddressByMemberId_QueryDSL(Member member) {
        return Optional.ofNullable(query.select(QMember.member).from(QMember.member).where(QMember.member.eq(member)).fetchOne());
    }
}