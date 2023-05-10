package com.app.happybox.entity.user;

import com.app.happybox.type.Gender;
import com.app.happybox.type.Role;
import com.app.happybox.type.UserStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MemberDTO {

//    Member 전체 컬럼
    private Long id;
    private String memberId;
    private String memberPassword;
    private Address memberAddress;
    private String memberEmail;
    private String memberPhoneNumber;
    private UserStatus memberStatus;
    private Role memberRole;
    private String memberName;
    private LocalDate memberBirth;
    private Gender memberGender;
    private Address memberDeliveryAddress;
    private String deliveryName;
    private String deliveryPhoneNumber;

//    Member와 Fetch조인하여 사용할 DTO 작성할 곳

}
