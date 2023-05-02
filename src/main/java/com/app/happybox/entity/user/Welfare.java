package com.app.happybox.entity.user;

import com.app.happybox.entity.subscript.Subscription;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity @Table(name = "TBL_WELFARE")
@DiscriminatorValue("WELFARE")
@Getter @ToString(callSuper = true) @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Welfare extends User {

    /* 복지관의 구독 상품 기본적으로 복지관 당 하나 (OneToOne) */
    @OneToOne(fetch = FetchType.LAZY,mappedBy = "welfare", orphanRemoval = true)
    private Subscription subscription;
}
