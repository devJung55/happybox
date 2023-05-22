package com.app.happybox.repository.user;

import com.app.happybox.domain.AddressDTO;
import com.app.happybox.entity.user.Address;
import com.app.happybox.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserQueryDsl {

//    아이디중복체크
    public Boolean existsUserByUserId(String userId);

//    이메일 중복체크
    public Boolean existsUserByUserEmail(String userEmail);

//    휴대폰 중복체크
    public Boolean existsUserByUserPhoneNumber(String userPhoneNumber);

//    id로 회원조회(UserDetailService)
    public Optional<User> findByUserId(String userName);

//    이메일로 유저 찾기(Mail Send 용)
    public Optional<User> findByUserEmail(String userEmail);

}
