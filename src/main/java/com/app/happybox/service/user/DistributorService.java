package com.app.happybox.service.user;

import com.app.happybox.entity.user.Distributor;

public interface DistributorService {
//    회원정보수정
    public void updateDistributorInfoById(Distributor distributor);

//    회원탈퇴
    public void updateUserStatusById(Long distributorId);
}
