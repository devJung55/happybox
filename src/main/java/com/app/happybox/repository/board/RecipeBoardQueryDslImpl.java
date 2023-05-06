package com.app.happybox.repository.board;

import com.app.happybox.entity.board.QRecipeBoard;
import com.app.happybox.entity.board.QRecipeBoardDTO;
import com.app.happybox.entity.board.RecipeBoardDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.app.happybox.entity.board.QRecipeBoard.recipeBoard;

@RequiredArgsConstructor
public class RecipeBoardQueryDslImpl implements RecipeBoardQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public List<RecipeBoardDTO> findRecipeBoardListByMemberIdWithPaging_QueryDSL(Pageable pageable, Long memberId) {
        List<RecipeBoardDTO> recipeBoardDTOList = query.select(new QRecipeBoardDTO(
                recipeBoard.id,
                recipeBoard.member.memberName,
                recipeBoard.boardTitle,
                recipeBoard.boardContent,
                recipeBoard.createdDate,
                recipeBoard.recipeLikeCount.longValue(),
                recipeBoard.recipeBoardReplies.size().longValue()
        ))
                .from(recipeBoard)
                .where(recipeBoard.member.id.eq(memberId))
                .orderBy(recipeBoard.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return recipeBoardDTOList;
    }
}
