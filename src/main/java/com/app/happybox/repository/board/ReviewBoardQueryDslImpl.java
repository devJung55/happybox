package com.app.happybox.repository.board;

import com.app.happybox.entity.board.QReviewBoardDTO;
import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.entity.board.ReviewBoardDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static com.app.happybox.entity.board.QReviewBoard.reviewBoard;

@RequiredArgsConstructor
public class ReviewBoardQueryDslImpl implements ReviewBoardQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public Slice<ReviewBoard> findAllByIdDescWithPaging_QueryDSL(Pageable pageable) {
        List<ReviewBoard> reviewBoards =  query.select(reviewBoard)
                .from(reviewBoard)
                .join(reviewBoard.member).fetchJoin()
                .join(reviewBoard.subscription.welfare).fetchJoin()
                .join(reviewBoard.boardFiles).fetchJoin()
                .join(reviewBoard.reviewBoardReplies).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new SliceImpl<>(reviewBoards, pageable, true);
    }

    @Override
    public Slice<ReviewBoardDTO> findAllByLikeCountDescWithPaging_QueryDSL(Pageable pageable) {
        List<ReviewBoardDTO> reviewBoardDTOS = query.select(new QReviewBoardDTO(
                reviewBoard.id,
                reviewBoard.member.memberName,
                reviewBoard.subscription.welfare.welfareName,
                reviewBoard.boardTitle,
                reviewBoard.boardContent,
                reviewBoard.updatedDate,
                reviewBoard.reviewRating,
                reviewBoard.reviewLikeCount.longValue(),
                reviewBoard.reviewBoardReplies.size().longValue()
        ))
                .from(reviewBoard)
                .orderBy(reviewBoard.reviewLikeCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new SliceImpl<>(reviewBoardDTOS, pageable, true);
    }

    @Override
    public Optional<ReviewBoard> findById_QueryDSL(Long id) {
        return Optional.ofNullable(query.select(reviewBoard)
                .from(reviewBoard)
                .join(reviewBoard.member).fetchJoin()
                .join(reviewBoard.subscription).fetchJoin()
                .join(reviewBoard.boardFiles).fetchJoin()
                .where(reviewBoard.id.eq(id))
                .fetchOne());
    }

    @Override
    public Page<ReviewBoard> findAllByMemberIdDescWithPaging_QueryDSL(Pageable pageable, Long memberId) {
        List<ReviewBoard> reviewBoardList = query.select(reviewBoard)
                .from(reviewBoard)
                .join(reviewBoard.boardFiles).fetchJoin()
                .join(reviewBoard.member).fetchJoin()
                .where(reviewBoard.member.id.eq(memberId))
                .orderBy(reviewBoard.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(reviewBoard.id.count()).from(reviewBoard).where(reviewBoard.member.id.eq(memberId)).fetchOne();

        return new PageImpl<>(reviewBoardList, pageable, count);
    }

}
