package com.app.happybox.service.subscript;

import com.app.happybox.domain.welfare.RiderDTO;
import com.app.happybox.entity.subscript.Rider;
import com.app.happybox.type.DeliveryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RiderService {
//    라이더 등록
    public void registerRiderByWelfareId(RiderDTO riderDTO);

//    라이더 조회
    public Page<RiderDTO> getRiderListByWelfareIdWithPaging(Pageable pageable, Long welfareId);

//    배달상태 변경
    public void updateDeliveryStatusById(Long riderId, DeliveryType deliveryStatus);

//    배달원 수
    public Long getRiderCountByWelfareId(Long welfareId);

    default Rider toRiderEntity(RiderDTO riderDTO){
        return Rider.builder()
                .riderName(riderDTO.getRiderName())
                .riderPhoneNumber(riderDTO.getRiderPhoneNumber())
                .fileOrgName(riderDTO.getFileOrgName())
                .filePath(riderDTO.getFilePath())
                .fileUuid(riderDTO.getFileUuid())
                .build();
    }

    default RiderDTO toRiderDTO(Rider rider){
        return RiderDTO.builder()
                .id(rider.getId())
                .riderName(rider.getRiderName())
                .riderPhoneNumber(rider.getRiderPhoneNumber())
                .fileOrgName(rider.getFileOrgName())
                .filePath(rider.getFilePath())
                .fileUuid(rider.getFileUuid())
                .deliveryStatus(rider.getDeliveryStatus())
                .welfareId(rider.getWelfare().getId())
                .build();
    }

}
