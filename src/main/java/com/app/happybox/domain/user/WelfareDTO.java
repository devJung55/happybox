package com.app.happybox.domain.user;

import com.app.happybox.entity.user.Address;
import com.app.happybox.type.Gender;
import com.app.happybox.type.Role;
import com.app.happybox.type.UserStatus;
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
public class WelfareDTO {

//    Wefare 전체 컬럼
    private Long id;
    private String userId;
    private String userPassword;
    private Address address;
    private String userEmail;
    private String userPhoneNumber;
    private UserStatus userStatus;
    private Role userRole;
    private String welfareName;
    private Integer welfarePointTotal;

    private LocalDateTime createdDate;

//    연관된 DTO 작성

    @Builder
    public WelfareDTO(Long id, String userId, String userPassword, Address address, String userEmail, String userPhoneNumber, UserStatus userStatus, Role userRole, String welfareName, Integer welfarePointTotal, LocalDateTime createdDate) {
        this.id = id;
        this.userId = userId;
        this.userPassword = userPassword;
        this.address = address;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
        this.userStatus = userStatus;
        this.userRole = userRole;
        this.welfareName = welfareName;
        this.welfarePointTotal = welfarePointTotal;
        this.createdDate = createdDate;
    }
}
