package com.app.happybox.entity.file;

import com.app.happybox.entity.board.Board;
import com.app.happybox.entity.board.DonationBoard;
import com.app.happybox.entity.board.RecipeBoard;
import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.type.FileRepresent;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity @Table(name = "TBL_BOARD_FILE")
@DynamicInsert
@Getter @ToString @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardFile extends Files {
    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;

    //    파일이 대표 파일인지 여부
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'ORDINARY'")
    private FileRepresent fileRepresent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private ReviewBoard reviewBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private RecipeBoard recipeBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private DonationBoard donationBoard;

    @Builder
    public BoardFile(String filePath, String fileUuid, String fileOrgName, Long id, FileRepresent fileRepresent, ReviewBoard reviewBoard, RecipeBoard recipeBoard, DonationBoard donationBoard) {
        super(filePath, fileUuid, fileOrgName);
        this.id = id;
        this.fileRepresent = fileRepresent;
        this.reviewBoard = reviewBoard;
        this.recipeBoard = recipeBoard;
        this.donationBoard = donationBoard;
    }

    public void setReviewBoard(ReviewBoard reviewBoard) {
        this.reviewBoard = reviewBoard;
    }

    public  void setRecipeBoard(RecipeBoard recipeBoard) {
        this.recipeBoard = recipeBoard;
    }

    public void setDonationBoard(DonationBoard donationBoard) {
        this.donationBoard = donationBoard;
    }

    public void setFileRepresent(FileRepresent fileRepresent) {
        this.fileRepresent = fileRepresent;
    }
}
