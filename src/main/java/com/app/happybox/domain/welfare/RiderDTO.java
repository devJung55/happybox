package com.app.happybox.domain.welfare;

import com.app.happybox.type.DeliveryType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @ToString @Setter
public class RiderDTO {

    private Long id;
    private String riderName;
    private String riderPhoneNumber;
    private DeliveryType deliveryStatus;

    private String filePath;
    private String fileUuid;
    private String fileOrgName;

    private Long welfareId;


    @Builder
    public RiderDTO(Long id, String riderName, String riderPhoneNumber, DeliveryType deliveryStatus, String filePath, String fileUuid, String fileOrgName, Long welfareId) {
        this.id = id;
        this.riderName = riderName;
        this.riderPhoneNumber = riderPhoneNumber;
        this.deliveryStatus = deliveryStatus;
        this.filePath = filePath;
        this.fileUuid = fileUuid;
        this.fileOrgName = fileOrgName;
        this.welfareId = welfareId;
    }
}
