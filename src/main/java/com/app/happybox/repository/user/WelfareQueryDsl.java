package com.app.happybox.repository.user;

import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.Welfare;
import com.querydsl.core.Tuple;

import java.util.Optional;

public interface WelfareQueryDsl {

    //    회원정보수정
    public void setWelfareInfoById_QueryDSL(Welfare welfare);

    //    로그인을 위해 ID로 Identification, Password 조회
    public Tuple findWelfareInfoById(Long id);

    //    가입여부 확인
    public Optional<Member> findWelfareByWelfarePhone(String welfarePhone);

    //    아이디 중복체크
    public Boolean checkId(String welfareId);
}
