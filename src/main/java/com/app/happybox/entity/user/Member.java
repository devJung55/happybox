package com.app.happybox.entity.user;

import com.app.happybox.entity.welfare.SubscriptUser;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity @Table(name = "TBL_MEMBER")
@Getter @ToString(callSuper = true) @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends User{

    /* 일반회원만 있는 정보 */
    @NotNull
    private String memberName;
    @NotNull
    private String memberMobile;
    @NotNull
    private LocalDate memberBirth;
    @NotNull @Enumerated(EnumType.STRING)
    private Gender memberGender;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<SubscriptUser> subscriptUsers;

    /* 생성자 */
    public Member(String userId, String userPassword, Address address, String userEmail, UserStatus userStatus, String memberName, String memberMobile, LocalDate memberBirth, Gender memberGender) {
        super(userId, userPassword, address, userEmail, userStatus);
        this.memberName = memberName;
        this.memberMobile = memberMobile;
        this.memberBirth = memberBirth;
        this.memberGender = memberGender;
    }
}
