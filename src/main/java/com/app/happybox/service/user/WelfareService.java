package com.app.happybox.service.user;

import com.app.happybox.entity.user.Welfare;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WelfareService {
//    회원정보수정
    public void updateWelfareInfoById(Welfare welfare);

//    회원탈퇴
    public void updateUserStatusById(Long welfareId);

//    관리자 복지관회원 목록
    public Page<Welfare> getList(Pageable pageable);
}
