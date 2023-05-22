package com.app.happybox.repository.user;

import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.Welfare;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.Optional;

public interface WelfareQueryDsl {

    //    회원정보수정
    public void setWelfareInfoById_QueryDSL(Welfare welfare);

//    복지관 로그인
    public Optional<Welfare> logIn(String welfareId, String welfarePassword);

//    복지관 회원 목록
    public Page<Welfare> findAllWithPaging_QueryDSL(Pageable pageable);

//    복지관회원 조회
    public Optional<Welfare> findWelfareById_QueryDSL(Long welfareId);

    // 이름으로 검색
    public Slice<Welfare> findByWelfareName_QueryDSL(Pageable pageable, String welfareName);
}
