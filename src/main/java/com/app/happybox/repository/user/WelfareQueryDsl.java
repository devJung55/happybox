package com.app.happybox.repository.user;

import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.Welfare;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface WelfareQueryDsl {

    //    회원정보수정
    public void setWelfareInfoById_QueryDSL(Welfare welfare);

//    복지관 로그인
    public Optional<Welfare> logIn(String welfareId, String welfarePassword);

//    복지관 회원 목록
    public Page<Welfare> findAllWithPaging_QueryDSL(Pageable pageable);
}
