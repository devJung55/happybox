package com.app.happybox.repository.user;

import com.app.happybox.domain.AddressDTO;
import com.app.happybox.entity.user.Address;

import java.util.Optional;

public interface UserQueryDsl {
    //    id로 주소 조회
    public Optional<Address> findAddressById(Long id);
}
