package com.app.happybox.repository.user;

import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.QMember;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
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
        return Optional.ofNullable(query.select(member)
                .from(member)
                .where(member.userId.eq(memberId).and(member.userPassword.eq(memberPassword)))
                .fetchOne());
    }

    //    아이디 찾기(memberPhone)
    @Override
    public Optional<String> findMemberIdByPhoneNumber(String userPhoneNumber) {
        String id = query.select(member.userId)
                .from(member)
                .where(member.userPhoneNumber.eq(userPhoneNumber))
                .fetchOne();
        //    조회 했을 때 정보가 없는지 여부 판단
        if (id != null) {
            return Optional.of(id);
        } else {
            return Optional.of("");
        }
    }

    //    아이디 찾기(memberEmail)
    @Override
    public Optional<String> findMemberIdByEmail(String memberEmail) {
        return Optional.ofNullable(query.select(member.userId)
        .from(member)
        .where(member.userEmail.eq(memberEmail))
        .fetchOne());
    }

    //    비밀번호 찾기(memberPhone)
    @Override
    public Optional<String> findMemberPwByPhoneNumber(String memberPhoneNumber) {
        return Optional.ofNullable(query.select(member.userPassword)
                .from(member)
                .where(member.userPhoneNumber.eq(memberPhoneNumber))
                .fetchOne());
    }

    //    마이페이지 배송지정보
    @Override
    public Optional<Member> findDeliveryAddressByMemberId_QueryDSL(Long memberId) {
        return Optional.ofNullable(query.select(member).from(member).where(member.eq(member)).fetchOne());
    }

    @Override
    public Page<Member> findAllWithPaging_QueryDSL(Pageable pageable) {
        List<Member> memberList = query.select(member)
                .from(member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(member.id.count()).from(member).fetchOne();

        return new PageImpl<>(memberList, pageable, count);
    }

    @Override
    public Optional<Member> findMemberById_QueryDSL(Long memberId) {
        Optional<Member> memberDatail = Optional.ofNullable(query.select(member)
                .from(member)
                .where(member.id.eq(memberId))
                .fetchOne());
        return memberDatail;
    }


}