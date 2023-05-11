package com.app.happybox.service.subscript;

import com.app.happybox.entity.subscript.Rider;
import com.app.happybox.type.DeliveryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RiderService {
//    라이더 등록
    public void registerRiderByWelfareId(Rider rider);

//    라이더 조회
    public Page<Rider> getRiderListByWelfareIdWithPaging(Pageable pageable, Long welfareId);

//    배달상태 변경
    public void updateDeliveryStatusById(Long riderId, DeliveryType deliveryStatus);
}
