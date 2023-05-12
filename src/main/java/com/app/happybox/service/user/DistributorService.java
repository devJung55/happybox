package com.app.happybox.service.user;

import com.app.happybox.entity.user.Distributor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DistributorService {
//    회원정보수정
    public void updateDistributorInfoById(Distributor distributor);

//    회원탈퇴
    public void updateUserStatusById(Long distributorId);

//    관리자 유통회원 목록
    public Page<Distributor> getList(Pageable pageable);

//    관리자 회원조회
    public Optional<Distributor> getDetail(Long distributorId);
}
