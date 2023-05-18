package com.app.happybox.repository.board;

import com.app.happybox.entity.file.QBoardFile;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.app.happybox.entity.file.QBoardFile.boardFile;

@RequiredArgsConstructor
public class BoardFileQueryDslImpl implements BoardFileQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public void deleteByReviewBoardId(Long id) {
        query.delete(boardFile).where(boardFile.id.eq(id)).execute();
    }
}
