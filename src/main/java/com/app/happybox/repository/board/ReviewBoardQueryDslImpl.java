package com.app.happybox.repository.board;

import com.app.happybox.entity.board.QReviewBoard;
import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.entity.board.ReviewBoardDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static com.app.happybox.entity.board.QRecipeBoard.recipeBoard;
import static com.app.happybox.entity.board.QReviewBoard.reviewBoard;


@RequiredArgsConstructor
public class ReviewBoardQueryDslImpl implements ReviewBoardQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public Optional<ReviewBoard> findById_QueryDSL(Long id) {
        ReviewBoard reviewBoard = query.select(QReviewBoard.reviewBoard)
                .from(QReviewBoard.reviewBoard)
                .join(QReviewBoard.reviewBoard.member).fetchJoin()
                .leftJoin(QReviewBoard.reviewBoard.reviewBoardFiles).fetchJoin()
                .where(QReviewBoard.reviewBoard.id.eq(id))
                .fetchOne();
        return Optional.ofNullable(reviewBoard);
    }

    @Override
    public List<ReviewBoard> findTop8OrderByDate_QueryDSL() {
        return query.select(reviewBoard)
                .from(reviewBoard)
                .join(reviewBoard.member).fetchJoin()
                .join(reviewBoard.reviewBoardFiles).fetchJoin()
                .fetch();
    }

    @Override
    public ReviewBoard getCurrentSequence_QueryDsl() {
        return query.select(reviewBoard)
                .from(reviewBoard)
                .orderBy(reviewBoard.id.desc())
                .limit(1)
                .fetchOne();
    }

    //    최신순
    @Override
    public Slice<ReviewBoard> findAllByIdDescWithPaging_QueryDSL(Pageable pageable) {
        List<ReviewBoard> reviewBoards =  query.select(reviewBoard)
                .from(reviewBoard)
                .join(reviewBoard.member).fetchJoin()
                .join(reviewBoard.reviewBoardFiles).fetchJoin()
                .orderBy(reviewBoard.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return checkLastPage(pageable, reviewBoards);
    }

//    인기순
    @Override
    public Slice<ReviewBoard> findAllByLikeCountDescWithPaging_QueryDSL(Pageable pageable) {
        List<ReviewBoard> reviewBoards = query.select(reviewBoard)
                .from(reviewBoard)
                .join(reviewBoard.member).fetchJoin()
                .join(reviewBoard.reviewBoardFiles).fetchJoin()
                .orderBy(reviewBoard.reviewLikeCount.desc())
                .orderBy(reviewBoard.reviewBoardReplyCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return checkLastPage(pageable, reviewBoards);
    }

    @Override
    public Page<ReviewBoard> findAllByMemberIdDescWithPaging_QueryDSL(Pageable pageable, Long memberId) {
        List<ReviewBoard> reviewBoardList = query.select(reviewBoard)
                .from(reviewBoard)
                .join(reviewBoard.member).fetchJoin()
                .join(reviewBoard.reviewBoardFiles).fetchJoin()
                .where(reviewBoard.member.id.eq(memberId))
                .orderBy(reviewBoard.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(reviewBoard.id.count()).from(reviewBoard).where(reviewBoard.member.id.eq(memberId)).fetchOne();

        return new PageImpl<>(reviewBoardList, pageable, count);
    }

    @Override
    public Page<ReviewBoard> findReviewBoardListDescWithPaging_QueryDSL(Pageable pageable) {
        List<ReviewBoard> reviewBoardList = query.select(reviewBoard)
                .from(reviewBoard)
                .join(reviewBoard.reviewBoardFiles).fetchJoin()
                .join(reviewBoard.member).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(reviewBoard.id.count()).from(reviewBoard).fetchOne();

        return new PageImpl<>(reviewBoardList, pageable, count);
    }

    //    hasNext true인지 false인지 체크하는 메소드(마지막 페이지 체크)
    private Slice<ReviewBoard> checkLastPage(Pageable pageable, List<ReviewBoard> reviewBoards) {

        boolean hasNext = false;

        // 조회한 결과 개수가 요청한 페이지 사이즈보다 크면 뒤에 더 있음, next = true
        if (reviewBoards.size() > pageable.getPageSize()) {
            hasNext = true;
            reviewBoards.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(reviewBoards, pageable, hasNext);
    }

}
