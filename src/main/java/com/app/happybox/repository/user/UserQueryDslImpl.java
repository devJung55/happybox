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
}
