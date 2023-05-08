package com.app.happybox.entity.board;

import com.app.happybox.entity.file.BoardFile;
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
@Getter @ToString @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DonationBoard extends Board {

    /* 작성한 유저 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Welfare welfare;

    public DonationBoard(String boardTitle, String boardContent) {
        super(boardTitle, boardContent);
    }

    public void setWelfare(Welfare welfare) {
        this.welfare = welfare;
    }

}
