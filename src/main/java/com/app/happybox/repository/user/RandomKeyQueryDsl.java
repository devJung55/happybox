package com.app.happybox.repository.user;

import com.app.happybox.entity.user.UserRandomKey;

public interface RandomKeyQueryDsl {
    //    회원의 가장 최근 랜덤키 찾기
    public UserRandomKey getLatestRandomKey_QueryDSL(Long id);
}
