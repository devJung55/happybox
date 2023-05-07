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

//    member정보 수정
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



    @Override
    public void setMemberStatusById_QueryDSL(Member member) {

    }
}