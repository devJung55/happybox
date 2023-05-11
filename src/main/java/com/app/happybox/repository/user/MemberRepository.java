package com.app.happybox.repository.user;

import com.app.happybox.entity.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberQueryDsl {
    //    아이디로 전체 정보 조회(MemberDetailService)
    public Optional<Member> findByUserId(String memberId);
}
