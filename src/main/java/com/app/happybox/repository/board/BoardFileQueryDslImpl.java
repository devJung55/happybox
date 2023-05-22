package com.app.happybox.repository.board;

import com.app.happybox.entity.file.QBoardFile;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.app.happybox.entity.file.QBoardFile.boardFile;

@RequiredArgsConstructor
public class BoardFileQueryDslImpl implements BoardFileQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public Long deleteByReviewBoardId(Long id) {
        return query.delete(boardFile).where(boardFile.reviewBoard.id.eq(id)).execute();
    }

    @Override
    public Long deleteByRecipeBoardId(Long id) {
        return query.delete(boardFile).where(boardFile.recipeBoard.id.eq(id)).execute();
    }

    @Override
    public Long deleteByDonationBoardId(Long id) {
        return query.delete(boardFile).where(boardFile.donationBoard.id.eq(id)).execute();
    }
}
