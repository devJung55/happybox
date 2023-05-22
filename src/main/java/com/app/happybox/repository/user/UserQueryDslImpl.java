package com.app.happybox.repository.user;

import com.app.happybox.domain.AddressDTO;
import com.app.happybox.entity.user.Address;
import com.app.happybox.entity.user.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.app.happybox.entity.user.QUser.*;

@RequiredArgsConstructor
public class UserQueryDslImpl implements UserQueryDsl {

    private final JPAQueryFactory query;

//    id로 주소 조회
    @Override
    public Optional<Address> findAddressById(Long id) {
        return Optional.ofNullable(query.select(user.address)
                .from(user)
                .where(user.id.eq(id))
                .fetchOne());
    }

    //    전화번호로 회원 id 찾기
    @Override
    public String findUserIdByUserPhone_QueryDSL(String userPhoneNumber) {
        return Optional.ofNullable(query.select(user.userId)
                .from(user)
                .where(user.userPhoneNumber.eq(userPhoneNumber))
                .fetchOne()).orElse("");
    }

    //    전화번호로 회원 이메일 찾기
    @Override
    public Optional<String> findUserEmailByUserPhone_QueryDSL(String userPhoneNumber) {
        return Optional.ofNullable(query.select(user.userEmail)
                .from(user)
                .where(user.userPhoneNumber.eq(userPhoneNumber))
                .fetchOne());
    }

    //    이메일로 회원 id 찾기
    @Override
    public String findUserIdByEmail_QueryDSL(String userEmail) {
        return Optional.ofNullable(query.select(user.userId)
                .from(user)
                .where(user.userEmail.eq(userEmail))
                .fetchOne()).orElse("");
    }
}
