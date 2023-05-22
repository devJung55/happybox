package com.app.happybox.repository.user;

import com.app.happybox.entity.user.Welfare;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WelfareRepository extends JpaRepository<Welfare, Long>, WelfareQueryDsl {

    //    아이디로 전체 정보 조회(MemberDetailService)
    public Optional<Welfare> findByUserId(String userId);
    //    아이디 중복검사
    public boolean existsIdByUserId(String userId);

    //    휴대폰 중복검사
    public boolean existsByUserPhoneNumber(String userPhoneNumber);

    //    이메일 중복검사
    public boolean existsByUserEmail(String userEmail);

}
