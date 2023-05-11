package com.app.happybox.service.subscript;

import com.app.happybox.entity.subscript.Rider;
import com.app.happybox.repository.subscript.RiderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Qualifier("rider")
public class RiderServiceImpl implements RiderService {
    private final RiderRepository riderRepository;

    @Override
    public void registerRiderByWelfareId(Rider rider) {
        riderRepository.save(rider);
    }

    @Override
    public Page<Rider> getRiderListByWelfareIdWithPaging(Pageable pageable, Long welfareId) {
        return riderRepository.findAllByWelfareIdWithPaging_QueryDSL(pageable, welfareId);
    }
}
