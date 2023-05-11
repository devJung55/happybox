package com.app.happybox.service.user;

import com.app.happybox.entity.user.Distributor;
import com.app.happybox.repository.user.DistributorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Qualifier("distributor") @Primary
public class DistributorServiceImpl implements DistributorService {
    private final DistributorRepository distributorRepository;

    @Override
    public void updateDistributorInfoById(Distributor distributor) {
        distributorRepository.setDistributorInfoById_QueryDSL(distributor);
    }
}
