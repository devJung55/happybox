package com.app.happybox.domain.user;

import com.app.happybox.entity.user.Address;
import com.app.happybox.type.Role;
import com.app.happybox.type.UserStatus;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter @NoArgsConstructor @Data
public class DistributorDTO {

    private Long id;
    private String userId;
    private String userPassword;
    private Address address;
    private String userEmail;
    private String userPhoneNumber;
    private UserStatus userStatus;
    private Role userRole;
    private String distributorName;

    private LocalDateTime createdDate;

    @Builder
    public DistributorDTO(Long id, String userId, String userPassword, Address address, String userEmail, String userPhoneNumber, UserStatus userStatus, Role userRole, String distributorName, LocalDateTime createdDate) {
        this.id = id;
        this.userId = userId;
        this.userPassword = userPassword;
        this.address = address;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
        this.userStatus = userStatus;
        this.userRole = userRole;
        this.distributorName = distributorName;
        this.createdDate = createdDate;
    }
}
