package com.app.happybox.domain.user;

import com.app.happybox.entity.user.Address;
import com.app.happybox.type.Gender;
import com.app.happybox.type.Role;
import com.app.happybox.type.UserStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Getter
@NoArgsConstructor
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
    private String memberBirth;
    private Gender memberGender;
    private Address memberDeliveryAddress;
    private String deliveryName;
    private String deliveryPhoneNumber;

//    Member와 Fetch조인하여 사용할 DTO 작성할 곳


    @Builder
    public MemberDTO(Long id, String userId, String userPassword, Address userAddress, String userEmail, String userPhoneNumber, UserStatus userStatus, Role userRole, String memberName, String memberBirth, Gender memberGender, Address memberDeliveryAddress, String deliveryName, String deliveryPhoneNumber) {
        this.id = id;
        this.userId = userId;
        this.userPassword = userPassword;
        this.userAddress = userAddress;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
        this.userStatus = userStatus;
        this.userRole = userRole;
        this.memberName = memberName;
        this.memberBirth = memberBirth;
        this.memberGender = memberGender;
        this.memberDeliveryAddress = memberDeliveryAddress;
        this.deliveryName = deliveryName;
        this.deliveryPhoneNumber = deliveryPhoneNumber;
    }
}
