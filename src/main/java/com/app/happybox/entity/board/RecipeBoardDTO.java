package com.app.happybox.entity.board;

import com.app.happybox.domain.user.MemberDTO;
import com.app.happybox.domain.user.UserFileDTO;
import com.app.happybox.entity.file.BoardFileDTO;
import com.app.happybox.entity.reply.ReplyDTO;
import com.app.happybox.entity.user.Member;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @ToString @Setter
public class RecipeBoardDTO {
    private Long id;

    /*-- 회원 정보 --*/
    private MemberDTO memberDTO;
    private String memberName;
    private Long memberId;
    private UserFileDTO userFileDTO;

    /*-- 레시피 게시물 정보*/
    private String boardTitle;
    private String boardContent;
    private LocalDate boardRegisterDate;

    /*-- 좋아요 수 --*/
    private Integer recipeLikeCount;

    /*-- 댓글 수 --*/
    private Integer recipeReplyCount;

    /*-- 파일 리스트 --*/
    private List<BoardFileDTO> recipeBoardFiles = new ArrayList<>();

    /*-- 댓글 리스트 --*/
    private List<ReplyDTO> replies;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public RecipeBoardDTO(){this.recipeBoardFiles = new ArrayList<>();
    }

    @Builder
    public RecipeBoardDTO(Long id, MemberDTO memberDTO, String boardTitle, String boardContent, LocalDate boardRegisterDate, Integer recipeLikeCount, Integer recipeReplyCount, List<BoardFileDTO> recipeBoardFiles, List<ReplyDTO> replies, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.memberDTO = memberDTO;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardRegisterDate = boardRegisterDate;
        this.recipeLikeCount = recipeLikeCount;
        this.recipeReplyCount = recipeReplyCount;
        this.recipeBoardFiles = recipeBoardFiles;
        this.replies = replies;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public void setMemberDTO(MemberDTO memberDTO) {
        this.memberDTO = memberDTO;
    }
    public void setUserFileDTO(UserFileDTO userFileDTO) {
        this.userFileDTO = userFileDTO;
    }
}
