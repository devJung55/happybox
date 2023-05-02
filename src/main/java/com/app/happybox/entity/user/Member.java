package com.app.happybox.entity.user;

import com.app.happybox.entity.board.Board;
import com.app.happybox.entity.subscript.SubscriptLike;
import com.app.happybox.entity.order.MemberSubscription;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity @Table(name = "TBL_MEMBER")
@DiscriminatorValue("MEMBER")
@Getter @ToString(callSuper = true, exclude = "subscriptLikes") @NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    /* 회원 구독 좋아요 목록 */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<SubscriptLike> subscriptLikes;

    /* 회원 게시글 목록 */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", orphanRemoval = true)
    private List<Board> boards;

    /* 생성자 */
    public Member(String userId, String userPassword, Address address, String userEmail, UserStatus userStatus, String memberName, String memberMobile, LocalDate memberBirth, Gender memberGender) {
        super(userId, userPassword, address, userEmail, userStatus);
        this.memberName = memberName;
        this.memberMobile = memberMobile;
        this.memberBirth = memberBirth;
        this.memberGender = memberGender;
    }
}
