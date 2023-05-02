package com.app.happybox.entity.user;

import com.app.happybox.audity.Period;
import com.app.happybox.entity.board.BoardLike;
import com.app.happybox.entity.order.MemberSubscription;
import com.app.happybox.entity.payment.Payment;
import com.app.happybox.entity.order.Purchase;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "TBL_USER")
@Getter @ToString(exclude = "boardLikes") @NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorColumn(name = "USER_TYPE")
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

    /* 게시글 좋아요 */
    @OneToMany(fetch =  FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<BoardLike> boardLikes = new ArrayList<>();

    /* 생성자 */
    public User(String userId, String userPassword, Address address, String userEmail, UserStatus userStatus) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.address = address;
        this.userEmail = userEmail;
        this.userStatus = userStatus;
    }
}
