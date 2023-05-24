package com.app.happybox.entity.board;

import com.app.happybox.entity.file.BoardFile;
import com.app.happybox.entity.reply.RecipeBoardReply;
import com.app.happybox.entity.user.Member;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "TBL_RECIPE_BOARD")
@DynamicInsert
@DynamicUpdate
@Getter @ToString(exclude = {"recipeBoardReplies", "recipeBoardLikes", "recipeBoardFiles", "member"}, callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeBoard extends Board {

    /* 레시피 게시판 정보 */
    @ColumnDefault(value = "0")
    private Integer recipeLikeCount;

    /* 댓글 갯수 */
    @ColumnDefault(value = "0")
    private Integer recipeBoardReplyCount;
    /* ============= */

    /* 작성한 유저 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    /* 게시글 댓글 List */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipeBoard", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<RecipeBoardReply> recipeBoardReplies = new ArrayList<>();

    /* 게시글 좋아요 List */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipeBoard", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<RecipeBoardLike> recipeBoardLikes = new ArrayList<>();

    /* 레시피 파일 List */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipeBoard", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<BoardFile> recipeBoardFiles = new ArrayList<>();

    @Builder
    public RecipeBoard(Long id, String boardTitle, String boardContent, Integer recipeLikeCount, Integer recipeBoardReplyCount, Member member, List<RecipeBoardReply> recipeBoardReplies, List<RecipeBoardLike> recipeBoardLikes, List<BoardFile> recipeBoardFiles) {
        super(id, boardTitle, boardContent, recipeBoardFiles);
        this.recipeLikeCount = recipeLikeCount;
        this.recipeBoardReplyCount = recipeBoardReplyCount;
        this.member = member;
        this.recipeBoardReplies = recipeBoardReplies;
        this.recipeBoardLikes = recipeBoardLikes;
        this.recipeBoardFiles = recipeBoardFiles;
    }


    public RecipeBoard(String boardTitle, String boardContent) {
        super(boardTitle, boardContent);
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setRecipeLikeCount(Integer recipeLikeCount) {
        this.recipeLikeCount = recipeLikeCount;
    }

    public void setRecipeBoardReplyCount(Integer recipeBoardReplyCount) {
        this.recipeBoardReplyCount = recipeBoardReplyCount;
    }
}
