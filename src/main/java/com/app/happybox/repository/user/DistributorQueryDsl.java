package com.app.happybox.repository.user;

import com.app.happybox.entity.user.Distributor;
import com.app.happybox.entity.user.Member;
import com.querydsl.core.Tuple;

import java.util.Optional;

public interface DistributorQueryDsl {
//    유통 회원정보수정
    public void setDistributorInfoById_QueryDSL(Distributor distributor);

    //    로그인을 위해 ID로 Identification, Password 조회
    public Tuple findDistributorInfoById(Long id);

    //    가입여부 확인
    public Optional<Distributor> findDistributorByDistributorName(String distributorName);

    //    아이디 중복체크
    public String checkId(String distributorId);
}
