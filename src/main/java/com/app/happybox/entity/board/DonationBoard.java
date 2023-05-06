package com.app.happybox.entity.board;

import com.app.happybox.entity.file.BoardFile;
import com.app.happybox.entity.user.Welfare;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 기본 게시판
 * */
@Entity @Table(name = "TBL_DONATION_BOARD")
@DynamicInsert @Inheritance(strategy = InheritanceType.JOINED)
@Getter @ToString(exclude = {"boardFiles"}) @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DonationBoard extends Board {

    /* 작성한 유저 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Welfare welfare;

    /* 게시판 파일 List */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<BoardFile> boardFiles;

}
