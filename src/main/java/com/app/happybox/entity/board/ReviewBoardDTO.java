package com.app.happybox.entity.board;

import com.app.happybox.domain.user.MemberDTO;
import com.app.happybox.domain.user.WelfareDTO;
import com.app.happybox.entity.file.BoardFile;
import com.app.happybox.entity.file.BoardFileDTO;
import com.app.happybox.entity.reply.ReplyDTO;
import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.subscript.SubscriptionDTO;
import com.app.happybox.entity.user.Member;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ToString
@Setter
@Getter
public class ReviewBoardDTO {
    private Long id;

    /*-- 회원 정보 --*/
    private MemberDTO memberDTO;

    /*-- 리뷰 복지관명 --*/
    private WelfareDTO welfareDTO;

    /*-- 게시글 정보 --*/
    private String boardTitle;
    private String boardContent;
    private LocalDate boardRegisterDate;

    /*-- 별점 --*/
    private Integer reviewRating;

    private Integer reviewLikeCount;

    private Integer reviewBoardReplyCount;

    private String welfareName;

    /*-- 파일 리스트 --*/
    private List<BoardFileDTO> reviewBoardFiles;

    /*-- 댓글 리스트 --*/
    private List<ReplyDTO> replies;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public ReviewBoardDTO(){
        this.reviewBoardFiles = new ArrayList<>();
    }

    @Builder
    public ReviewBoardDTO(Long id, MemberDTO memberDTO, String boardTitle, String boardContent, LocalDate boardRegisterDate, Integer reviewRating, Integer reviewLikeCount, Integer reviewBoardReplyCount, String welfareName, List<BoardFileDTO> reviewBoardFiles, List<ReplyDTO> replies, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.memberDTO = memberDTO;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardRegisterDate = boardRegisterDate;
        this.reviewRating = reviewRating;
        this.reviewLikeCount = reviewLikeCount;
        this.reviewBoardReplyCount = reviewBoardReplyCount;
        this.welfareName = welfareName;
        this.reviewBoardFiles = reviewBoardFiles;
        this.replies = replies;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public void setMemberDTO(MemberDTO memberDTO) {
        this.memberDTO = memberDTO;
    }

}
