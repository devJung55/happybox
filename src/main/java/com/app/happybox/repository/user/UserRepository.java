package com.app.happybox.repository.user;

import com.app.happybox.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

//    아이디중복체크
    public Boolean existsUserByUserId(String userId);

//    이메일 중복체크
    public Boolean existsUserByUserEmail(String userEmail);

//    휴대폰 중복체크
    public Boolean existsUserByUserPhoneNumber(String userPhoneNumber);

//
}
