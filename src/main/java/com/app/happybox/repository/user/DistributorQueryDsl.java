package com.app.happybox.repository.user;

import com.app.happybox.entity.user.Distributor;
import com.app.happybox.entity.user.Member;
import com.querydsl.core.Tuple;

import java.util.Optional;

public interface DistributorQueryDsl {
//    유통 회원정보수정
    public void setDistributorInfoById_QueryDSL(Distributor distributor);

    //    가입여부 확인
    public Optional<Distributor> findDistributorByDistributorName(String distributorName);

//    유통회원 로그인
    public Optional<Distributor> logIn(String distributorId, String distributorPassword);

}
