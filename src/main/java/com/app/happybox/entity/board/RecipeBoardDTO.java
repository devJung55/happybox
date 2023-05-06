package com.app.happybox.entity.board;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @ToString
public class RecipeBoardDTO {
    private Long id;

    /*-- 회원 정보 --*/
    private String memberId;

    /*-- 레시피 게시물 정보*/
    private String recipeBoardTitle;
    private String recipeBoardContent;
    private LocalDateTime recipeBoardRegisterDate;

    /*-- 좋아요 수 --*/
    private Long likeCount;

    /*-- 댓글 수 --*/
    private Long replyCount;

    @QueryProjection
    public RecipeBoardDTO(Long id, String memberId, String recipeBoardTitle, String recipeBoardContent, LocalDateTime recipeBoardRegisterDate, Long likeCount, Long replyCount) {
        this.id = id;
        this.memberId = memberId;
        this.recipeBoardTitle = recipeBoardTitle;
        this.recipeBoardContent = recipeBoardContent;
        this.recipeBoardRegisterDate = recipeBoardRegisterDate;
        this.likeCount = likeCount;
        this.replyCount = replyCount;
    }
}
