package com.app.happybox.service.user;

import com.app.happybox.entity.file.UserFile;

import java.util.Optional;

public interface UserFileService {
//    회원 프로필 조회
    public UserFile getDetail(Long userId);
}
