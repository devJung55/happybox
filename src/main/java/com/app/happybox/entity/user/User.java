package com.app.happybox.entity.user;

import com.app.happybox.audity.Period;
import com.app.happybox.entity.product.Order;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.List;

@Entity @Table(name = "TBL_USER")
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert/* @DynamicUpdate*/
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User extends Period {
    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;
    /* ===== 회원 기본 정보 ===== */
    // 아이디 중복 X
    @NotNull @Column(unique = true)
    private String userId;
    @NotNull
    private String userPassword;
    @Embedded
    private Address address;
    // 이메일 중복 X
    @NotNull @Column(unique = true)
    private String userEmail;
    /* ======================= */
    /* ===== 회원 상태 표시 ===== */
    // 회원 INSERT 시 기본값은 REGISTERED 이다.
    @Enumerated(EnumType.STRING)
    @ColumnDefault(value = "'REGISTERED'")
    private UserStatus userStatus;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user", orphanRemoval = true)
    private List<Order> orders;

    /* 생성자 */
    public User(String userId, String userPassword, Address address, String userEmail, UserStatus userStatus) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.address = address;
        this.userEmail = userEmail;
        this.userStatus = userStatus;
    }
}
