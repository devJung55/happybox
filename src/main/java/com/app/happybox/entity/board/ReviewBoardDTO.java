package com.app.happybox.entity.board;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Getter @ToString
public class ReviewBoardDTO {
    private Long id;

    /*-- 회원 정보 --*/
    private String memberName;

    /*-- 리뷰 복지관명 --*/
    private String welfareName;

    /*-- 게시글 정보 --*/
    private String reviewBoardTitle;
    private String reviewBoardContent;
    private LocalDateTime reviewBoardRegisterDate;

    /*-- 별점 --*/
    private Integer reviewRating;

    /*-- 좋아요 수 --*/
    private Long reviewLikeCount;

    /*-- 댓글 수 --*/
    private Long reviewReplyCount;


    @QueryProjection
    public ReviewBoardDTO(Long id, String memberName, String reviewBoardTitle, String reviewBoardContent, LocalDateTime reviewBoardRegisterDate, Integer reviewRating, Long reviewLikeCount, Long reviewReplyCount, String welfareName){
        this.id = id;
        this.memberName = memberName;
        this.welfareName = welfareName;
        this.reviewBoardTitle = reviewBoardTitle;
        this.reviewBoardContent = reviewBoardContent;
        this.reviewBoardRegisterDate = reviewBoardRegisterDate;
        this.reviewRating = reviewRating;
        this.reviewLikeCount = reviewLikeCount;
        this.reviewReplyCount = reviewReplyCount;
    }
}
