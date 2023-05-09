package com.app.happybox.repository.board;

import com.app.happybox.entity.board.RecipeBoard;
import com.app.happybox.entity.board.RecipeBoardLike;
import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.app.happybox.entity.board.QRecipeBoardLike.recipeBoardLike;
import static com.app.happybox.entity.board.QReviewBoardLike.reviewBoardLike;

@RequiredArgsConstructor
public class RecipeBoardLikeQueryDslImpl implements RecipeBoardLikeQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public boolean checkMemberLikesRecipeBoard_QueryDSL(Member member, RecipeBoard recipeBoard) {
        Long count = query.select(recipeBoardLike.count())
                .from(recipeBoardLike)
                .where(recipeBoardLike.member.eq(member).and(recipeBoardLike.recipeBoard.eq(recipeBoard)))
                .fetchOne();
        return count > 0;

    }

    @Override
    public Page<RecipeBoardLike> findBookmarkListWithMemberIdWithPaging_QueryDSL(Pageable pageable, Long memberId) {
//        List<RecipeBoardLike> recipeBoardList = query.select(recipeBoardLike)
//                .from(recipeBoardLike)
//                .join(recipeBoardLike.recipeBoard).fetchJoin()
//                .where(recipeBoardLike.user.id.eq(memberId))
//                .orderBy(recipeBoard.id.desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();

        List<RecipeBoardLike> recipeBoardList = query.select(recipeBoardLike)
                .from(recipeBoardLike)
                .where(recipeBoardLike.member.id.eq(memberId))
                .orderBy(recipeBoardLike.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(recipeBoardLike.count())
                .from(recipeBoardLike)
                .where(recipeBoardLike.member.id.eq(memberId))
                .fetchOne();

        return new PageImpl<>(recipeBoardList, pageable, count);
    }
}
