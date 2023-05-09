package com.app.happybox.repository.board;

import com.app.happybox.entity.board.QRecipeBoard;
import com.app.happybox.entity.board.QRecipeBoardDTO;
import com.app.happybox.entity.board.RecipeBoard;
import com.app.happybox.entity.board.RecipeBoardDTO;
import com.app.happybox.entity.file.BoardFile;
import com.app.happybox.entity.file.QBoardFile;
import com.app.happybox.entity.type.FileRepresent;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.app.happybox.entity.board.QRecipeBoard.recipeBoard;
import static com.app.happybox.entity.file.QBoardFile.boardFile;

@RequiredArgsConstructor
public class RecipeBoardQueryDslImpl implements RecipeBoardQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public Page<RecipeBoard> findRecipeBoardListByMemberIdWithPaging_QueryDSL(Pageable pageable, Long memberId) {
        List<RecipeBoard> recipeBoardList = query.select(recipeBoard)
                .from(recipeBoard)
                .join(recipeBoard.member).fetchJoin()
                .join(recipeBoard.boardFiles).fetchJoin()
//                .join(recipeBoard.recipeBoardReplies).fetchJoin()
                .where(recipeBoard.member.id.eq(memberId))
                .orderBy(recipeBoard.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(recipeBoard.id.count()).from(recipeBoard).where(recipeBoard.member.id.eq(memberId)).fetchOne();

        return new PageImpl<>(recipeBoardList, pageable, count);
    }

//    댓글 수
    @Override
    public List<RecipeBoard> findRecipeBoardReplyCountByMemberId_QueryDSL(Long memberId) {
        List<RecipeBoard> recipeBoardList = query.select(recipeBoard)
                .from(recipeBoard)
                .join(recipeBoard.recipeBoardReplies).fetchJoin()
                .where(recipeBoard.member.id.eq(memberId))
                .fetch();
        
        return recipeBoardList;
    }

    @Override
    public List<RecipeBoard> findTop5ByLikeCountWithRepresentFileOrderByLikeCount_QueryDSL() {
        List<RecipeBoard> recipeBoards = query.select(recipeBoard)
                .from(recipeBoard)
                .join(recipeBoard.boardFiles)
                .fetchJoin()
                .where(recipeBoard.boardFiles.any().fileRepresent.eq(FileRepresent.REPRESENT))
                .orderBy(recipeBoard.recipeLikeCount.desc())
                .limit(5L)
                .fetch();

        return recipeBoards;
    }
}
