package com.app.happybox.repository.user;

import com.app.happybox.entity.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberQueryDsl {
    //    아이디로 전체 정보 조회(MemberDetailService)
    public Optional<Member> findByUserId(String userId);
//    아이디 중복검사
    public boolean existsIdByUserId(String userId);

//    휴대폰 중복검사
    public boolean existsByUserPhoneNumber(String userPhoneNumber);

//    이메일 중복검사
    public boolean existsByUserEmail(String userEmail);
}
