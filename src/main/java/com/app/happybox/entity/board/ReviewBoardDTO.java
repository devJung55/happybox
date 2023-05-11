package com.app.happybox.entity.board;

import com.app.happybox.entity.file.BoardFile;
import com.app.happybox.entity.file.BoardFileDTO;
import com.app.happybox.entity.reply.ReplyDTO;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter @ToString
public class ReviewBoardDTO {
    private Long id;

    /*-- 회원 정보 --*/
    private String memberName;
    private Long memberId;

    /*-- 리뷰 복지관명 --*/
    private String welfareName;

    /*-- 게시글 정보 --*/
    private String reviewBoardTitle;
    private String reviewBoardContent;
    private LocalDate reviewBoardRegisterDate;

    /*-- 별점 --*/
    private Integer reviewRating;

    /*-- 좋아요 수 --*/
    private Long reviewLikeCount;

    /*-- 댓글 수 --*/
    private Long reviewBoardReplyCount;

    /*-- 파일 리스트 --*/
    List<BoardFileDTO> boardFiles;

    /*-- 댓글 리스트 --*/
    List<ReplyDTO> replies;

    @Builder
    public ReviewBoardDTO(Long id, String memberName, String welfareName, String reviewBoardTitle, String reviewBoardContent, LocalDate reviewBoardRegisterDate, Integer reviewRating, Long reviewLikeCount, Long reviewBoardReplyCount, List<BoardFileDTO> boardFiles) {
        this.id = id;
        this.memberName = memberName;
        this.welfareName = welfareName;
        this.reviewBoardTitle = reviewBoardTitle;
        this.reviewBoardContent = reviewBoardContent;
        this.reviewBoardRegisterDate = reviewBoardRegisterDate;
        this.reviewRating = reviewRating;
        this.reviewLikeCount = reviewLikeCount;
        this.reviewBoardReplyCount = reviewBoardReplyCount;
        this.boardFiles = boardFiles;
    }

    @Builder
    public ReviewBoardDTO(Long id, String memberName, Long memberId, String reviewBoardTitle, String reviewBoardContent, LocalDate reviewBoardRegisterDate, Integer reviewRating, List<BoardFileDTO> boardFiles) {
        this.id = id;
        this.memberName = memberName;
        this.memberId = memberId;
        this.reviewBoardTitle = reviewBoardTitle;
        this.reviewBoardContent = reviewBoardContent;
        this.reviewBoardRegisterDate = reviewBoardRegisterDate;
        this.reviewRating = reviewRating;
        this.boardFiles = boardFiles;
    }
}
