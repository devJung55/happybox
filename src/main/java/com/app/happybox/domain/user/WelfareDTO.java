package com.app.happybox.domain.user;

import com.app.happybox.entity.user.Address;
import com.app.happybox.type.UserStatus;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class WelfareDTO {

//    Wefare 전체 컬럼
    private Long id;
    private String welfareId;
    private String welfarePassword;
    private Address welfareAddress;
    private String welfareEmail;
    private String welfarePhoneNumber;
    private UserStatus welfareStatus;
    private String welfareName;
    private Integer welfarePointTotal;
    private Address welfareDeliveryAddress;
    private String deliveryName;
    private String deliveryPhoneNumber;

//    연관된 DTO 작성

}
