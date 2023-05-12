package com.app.happybox.entity.board;

import com.app.happybox.entity.file.BoardFileDTO;
import com.app.happybox.entity.reply.ReplyDTO;
import com.app.happybox.entity.user.Member;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter @ToString
public class RecipeBoardDTO {
    private Long id;

    /*-- 회원 정보 --*/
    private String memberName;
    private Long memberId;

    /*-- 레시피 게시물 정보*/
    private String recipeBoardTitle;
    private String recipeBoardContent;
    private LocalDateTime recipeBoardRegisterDate;

    /*-- 좋아요 수 --*/
    private Integer likeCount;

    /*-- 댓글 수 --*/
    private Integer replyCount;

    /*-- 파일 리스트 --*/
    List<BoardFileDTO> boardFiles;

    /*-- 댓글 리스트 --*/
    List<ReplyDTO> replies;

    @Builder
    public RecipeBoardDTO(Long id, String memberName, Long memberId, String recipeBoardTitle, String recipeBoardContent, LocalDateTime recipeBoardRegisterDate, Integer likeCount, Integer replyCount, List<BoardFileDTO> boardFiles, List<ReplyDTO> replies) {
        this.id = id;
        this.memberName = memberName;
        this.memberId = memberId;
        this.recipeBoardTitle = recipeBoardTitle;
        this.recipeBoardContent = recipeBoardContent;
        this.recipeBoardRegisterDate = recipeBoardRegisterDate;
        this.likeCount = likeCount;
        this.replyCount = replyCount;
        this.boardFiles = boardFiles;
        this.replies = replies;
    }

}
