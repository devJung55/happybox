package com.app.happybox.entity.user;

import com.app.happybox.audity.Period;
import com.app.happybox.entity.file.UserFile;
import com.app.happybox.entity.reply.Reply;
import com.app.happybox.entity.reply.ReplyLike;
import com.app.happybox.entity.type.UserStatus;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "TBL_USER")
@Getter @ToString(exclude = {"replies", "replyLikes"}) @NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @NotNull @Column(unique = true)
    private String userPhoneNumber;
    /* ======================= */
    /* ===== 회원 상태 표시 ===== */
    // 회원 INSERT 시 기본값은 REGISTERED 이다.
    @Enumerated(EnumType.STRING)
    @ColumnDefault(value = "'REGISTERED'")
    private UserStatus userStatus;

    /* 프로필 사진 */
    // OneToOne 관계에서 양방향으로 설정되어 있다면,
    // N + 1 문제가 발생한다. 따라서 단방향 관계로 제거했음.
//    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
//    private UserFile userFile;

    /* 댓글 List */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Reply> replies = new ArrayList<>();

    /* 댓글 좋아요 List */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<ReplyLike> replyLikes = new ArrayList<>();

    /* 회원 Random Key */
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true, cascade = CascadeType.REMOVE)
//    private List<UserRandomKey> userRandomKeys;

    public User(String userId, String userPassword, Address address, String userEmail, String userPhoneNumber) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.address = address;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
    }
}
