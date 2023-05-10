package com.app.happybox.entity.board;

import com.app.happybox.entity.file.BoardFile;
import com.app.happybox.entity.type.DonateType;
import com.app.happybox.entity.user.Welfare;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 기본 게시판
 * */
@Entity @Table(name = "TBL_DONATION_BOARD")
@DynamicInsert @Inheritance(strategy = InheritanceType.JOINED)
@Getter @ToString(callSuper = true) @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DonationBoard extends Board {
    @Enumerated(EnumType.STRING)
    private DonateType donateType;

    /* 기부할 급식소 */
    private String donateLocation;

    /* 작성한 유저 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Welfare welfare;

    public DonationBoard(String boardTitle, String boardContent, DonateType donateType, String donateLocation) {
        super(boardTitle, boardContent);
        this.donateType = donateType;
        this.donateLocation = donateLocation;
    }

    public void setWelfare(Welfare welfare) {
        this.welfare = welfare;
    }

}
