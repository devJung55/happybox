package com.app.happybox.repository.user;

import com.app.happybox.entity.user.Distributor;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.QDistributor;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.app.happybox.entity.user.QDistributor.distributor;


@RequiredArgsConstructor
public class DistributorQueryDslImpl implements DistributorQueryDsl {
    private final JPAQueryFactory query;

//    유통 회원 정보 수정
    @Override
    public void setDistributorInfoById_QueryDSL(Distributor distributor) {
        query.update(QDistributor.distributor)
                .set(QDistributor.distributor.userPassword, distributor.getUserPassword())
                .set(QDistributor.distributor.distributorName, distributor.getDistributorName())
                .set(QDistributor.distributor.userPhoneNumber, distributor.getUserPhoneNumber())
                .set(QDistributor.distributor.address, distributor.getAddress())
                .set(QDistributor.distributor.userEmail, distributor.getUserEmail())
                .where(QDistributor.distributor.eq(distributor))
                .execute();
    }


//    유통회원이름으로 유통회원 정보 조회
    @Override
    public Optional<Distributor> findDistributorByDistributorName(String distributorName) {
        Distributor distributor = query.select(QDistributor.distributor)
                .from(QDistributor.distributor)
                .where(QDistributor.distributor.distributorName.eq(distributorName))
                .fetchOne();
        return Optional.ofNullable(distributor);
    }

//    유통 로그인
    @Override
    public Optional<Distributor> logIn(String distributorId, String distributorPassword) {
        return Optional.ofNullable(query.select(distributor)
                .from(distributor)
                .where(distributor.userId.eq(distributorId).and(distributor.userPassword.eq(distributorPassword)))
                .fetchOne());
    }

    @Override
    public Page<Distributor> findAllWithPaging_QueryDSL(Pageable pageable) {
        List<Distributor> distributorList = query.select(distributor)
                .from(distributor)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(distributor.id.count()).from(distributor).fetchOne();

        return new PageImpl<>(distributorList, pageable, count);
    }

    @Override
    public Optional<Distributor> findDistributorById_QueryDSL(Long distributorId) {
        Optional<Distributor> distributor = Optional.ofNullable(query.select(QDistributor.distributor)
                .from(QDistributor.distributor)
                .where(QDistributor.distributor.id.eq(distributorId))
                .fetchOne());

        return distributor;
    }
}