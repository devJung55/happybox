package com.app.happybox.service.subscript;

import com.app.happybox.domain.welfare.RiderDTO;
import com.app.happybox.entity.subscript.Rider;
import com.app.happybox.entity.user.Welfare;
import com.app.happybox.repository.subscript.RiderRepository;
import com.app.happybox.repository.user.WelfareRepository;
import com.app.happybox.service.user.WelfareService;
import com.app.happybox.type.DeliveryType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Qualifier("rider")
@Slf4j
public class RiderServiceImpl implements RiderService {
    private final RiderRepository riderRepository;
    private final WelfareRepository welfareRepository;

    @Override
    public void registerRiderByWelfareId(RiderDTO riderDTO) {
        log.info("서비스에 들어왔냐??========================");
        log.info(riderDTO.toString());
        Rider rider = toRiderEntity(riderDTO);
        welfareRepository.findById(riderDTO.getWelfareId()).ifPresent(welfare -> rider.setWelfare(welfare));
        riderRepository.save(rider);
    }

    @Override
    public Page<Rider> getRiderListByWelfareIdWithPaging(Pageable pageable, Long welfareId) {
        return riderRepository.findAllByWelfareIdWithPaging_QueryDSL(pageable, welfareId);
    }

    @Override
    public void updateDeliveryStatusById(Long riderId, DeliveryType deliveryStatus) {
        Rider rider = riderRepository.findById(riderId).get();
        rider.setDeliveryStatus(deliveryStatus);
    }
}
