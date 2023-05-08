package com.app.happybox.repository.board;

import com.app.happybox.entity.board.QReviewBoard;
import com.app.happybox.entity.file.BoardFile;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.app.happybox.entity.board.QReviewBoard.*;

@RequiredArgsConstructor
public class ReviewBoardFileQueryDslImpl implements ReviewBoardFileQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public List<BoardFile> findByReviewBoardId(Long id) {
        return null;
    }
}
