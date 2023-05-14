package com.app.happybox.repository.user;

import com.app.happybox.entity.file.UserFile;

import java.util.Optional;

public interface UserFileQueryDsl {
//    회원 프로필 사진 조회
    public UserFile findById_QueryDSL(Long userId);
}
