package com.app.happybox.repository.user;

import com.app.happybox.domain.AddressDTO;
import com.app.happybox.entity.user.Address;

import java.util.Optional;

public interface UserQueryDsl {
    //    id로 주소 조회
    public Optional<Address> findAddressById(Long id);

    //    전화번호로 회원 id 찾기
    public String findUserIdByUserPhone_QueryDSL(String userPhoneNumber);

    //    전화번호로 회원 이메일 찾기
    public Optional<String> findUserEmailByUserPhone_QueryDSL(String userPhoneNumber);

    //    이메일로 회원 id 찾기
    public String findUserIdByEmail_QueryDSL(String userEmail);
}
