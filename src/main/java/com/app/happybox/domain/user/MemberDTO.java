package com.app.happybox.domain.user;

import com.app.happybox.entity.user.Address;
import com.app.happybox.entity.user.Member;
import com.app.happybox.type.Gender;
import com.app.happybox.type.Role;
import com.app.happybox.type.UserStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Getter
@NoArgsConstructor
public class MemberDTO implements Serializable {

//    Member 전체 컬럼
    private Long id;
    private String userId;
    private String userPassword;
    private Address address;
    private String userEmail;
    private String userPhoneNumber;
    private UserStatus userStatus;
    private Role userRole;
    private String memberName;
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private LocalDate memberBirth;
    private Gender memberGender;
    private Address memberDeliveryAddress;
    private String deliveryName;
    private String deliveryPhoneNumber;
    private UserFileDTO userFileDTO;

//    Member와 Fetch조인하여 사용할 DTO 작성할 곳

    @Builder
    public MemberDTO(Long id, String userId, String userPassword, Address address, String userEmail, String userPhoneNumber, UserStatus userStatus, Role userRole, String memberName, LocalDate memberBirth, Gender memberGender, Address memberDeliveryAddress, String deliveryName, String deliveryPhoneNumber) {
        this.id = id;
        this.userId = userId;
        this.userPassword = userPassword;
        this.address = address;
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

//    OAuth 용
    public MemberDTO(Member member) {
        this.userEmail = member.getUserEmail();
        this.userPhoneNumber = member.getUserPhoneNumber();
        this.memberName = member.getMemberName();
    }

    public void setUserFileDTO(UserFileDTO userFileDTO) {
        this.userFileDTO = userFileDTO;
    }
}
