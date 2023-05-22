package com.app.happybox.service.user;

import com.app.happybox.entity.user.User;
import com.app.happybox.entity.user.UserRandomKey;

public interface RandomKeyService {
    //    랜덤키 생성
    public UserRandomKey saveRandomKey(User user);

    //    가장 최근 랜덤키 불러오기
    public UserRandomKey getLatestRandomKey(Long id);

    //    랜덤키 업데이트
    public void updateRandomKey(Long id);
}
