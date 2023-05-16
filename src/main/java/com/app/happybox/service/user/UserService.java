package com.app.happybox.service.user;

public interface UserService {
//    관리자 회원 삭제
    public void deleteByMemberId(Long userId);

    //    아이디중복체크
    public Boolean existsUserByUserId(String userId);

    //    이메일 중복체크
    public Boolean existsUserByUserEmail(String userEmail);

    //    휴대폰 중복체크
    public Boolean existsUserByUserPhoneNumber(String userPhoneNumber);
}
