package com.app.happybox.entity.user;

import com.app.happybox.entity.type.Gender;
import com.app.happybox.entity.type.UserStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MemberDTO {

    private Long id;
    private String memberId;
    private String memberPassword;
    private Address memberAddress;
    private String memberEmail;
    private String memberPhoneNumber;
    private UserStatus memberStatus;
    private String memberName;
    private LocalDate memberBirth;
    private Gender memberGender;
    private Address memberDeliveryAddress;
    private String deliveryName;
    private String deliveryPhoneNumber;

}
