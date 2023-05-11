package com.app.happybox.domain.user;

import com.app.happybox.entity.user.Address;
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
    private String userId;
    private String userPassword;
    private Address userAddress;
    private String userEmail;
    private String userPhoneNumber;
    private UserStatus userStatus;
    private Role userRole;
    private String memberName;
    private LocalDate memberBirth;
    private Gender memberGender;
    private Address memberDeliveryAddress;
    private String deliveryName;
    private String deliveryPhoneNumber;

//    Member와 Fetch조인하여 사용할 DTO 작성할 곳

}
