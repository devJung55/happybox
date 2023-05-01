package com.app.happybox.entity.user;

import com.app.happybox.entity.welfare.Subscript;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity @Table(name = "TBL_WELFARE")
@Getter @ToString(callSuper = true) @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Welfare extends User {

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    private Subscript subscript;
}
