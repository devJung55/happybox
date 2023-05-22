package com.app.happybox.entity.board;

import com.app.happybox.entity.file.BoardFile;
import com.app.happybox.type.DonateType;
import com.app.happybox.entity.user.Welfare;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 기본 게시판
 * */
@Entity @Table(name = "TBL_DONATION_BOARD")
@DynamicInsert
@DynamicUpdate
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

    /* 기부 파일 List */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "donationBoard", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<BoardFile> donationBoardFiles = new ArrayList<>();

    @Builder
    public DonationBoard(Long id, String boardTitle, String boardContent, List<BoardFile> donationBoardFiles, DonateType donateType, String donateLocation, Welfare welfare) {
        super(id, boardTitle, boardContent, donationBoardFiles);
        this.donateType = donateType;
        this.donateLocation = donateLocation;
        this.welfare = welfare;
    }


    public void setWelfare(Welfare welfare) {
        this.welfare = welfare;
    }

    public void setDonateType(DonateType donateType) {
        this.donateType = donateType;
    }
}
