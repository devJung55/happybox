package com.app.happybox.repository.user;

import com.app.happybox.entity.user.Distributor;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.QDistributor;
import com.app.happybox.entity.user.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.app.happybox.entity.user.QDistributor.distributor;

@RequiredArgsConstructor
public class DistributorQueryDslImpl implements DistributorQueryDsl {
    private final JPAQueryFactory query;

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
}