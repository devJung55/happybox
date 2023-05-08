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

    @Override
    public void setMemberStatusById_QueryDSL(Member member) {
        
    }

    /* id로 ID,Password 조회 */
    @Override
    public Tuple findMemberInfoById(Long id) {
        return query.select(member.userId, member.userPassword)
                .from(member)
                .where(member.id.eq(id))
                .fetchOne();
    }

    //    phone으로 member 유무 확인
    @Override
    public Optional<Member> findMemberByMemberPhone(String MemberPhone) {
        Member member = query.select(QMember.member)
                .from(QMember.member)
                .where(QMember.member.userPhoneNumber.eq(MemberPhone))
                .fetchOne();
        return Optional.ofNullable(member);
    }

//    아이디 중복체크

    @Override
    public Boolean checkId(String memberId) {

        String identification = query.select(member.userId)
                .from(member)
                .where(member.userId.eq(memberId))
                .fetchOne();

        if (identification != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<Member> findDeliveryAddressByMemberId_QueryDSL(Member member) {
        return Optional.ofNullable(query.select(QMember.member).from(QMember.member).where(QMember.member.eq(member)).fetchOne());
    }
}