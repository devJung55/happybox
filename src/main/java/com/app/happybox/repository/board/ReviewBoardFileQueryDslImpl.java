package com.app.happybox.repository.board;

import com.app.happybox.entity.file.BoardFile;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
public class ReviewBoardFileQueryDslImpl implements ReviewBoardFileQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public List<BoardFile> findByReviewBoardId(Long id) {
        return null;
    }
}
