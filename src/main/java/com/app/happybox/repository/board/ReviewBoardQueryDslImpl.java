package com.app.happybox.repository.board;

import com.app.happybox.entity.board.QReviewBoard;
import com.app.happybox.entity.board.QReviewBoardDTO;
import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.entity.board.ReviewBoardDTO;
import com.app.happybox.entity.user.Member;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;
import java.util.Optional;

import static com.app.happybox.entity.board.QReviewBoard.reviewBoard;

@RequiredArgsConstructor
public class ReviewBoardQueryDslImpl implements ReviewBoardQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public Slice<ReviewBoardDTO> findAllByDateDescWithPaging_QueryDSL(Pageable pageable) {
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
                .orderBy(reviewBoard.updatedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new SliceImpl<>(reviewBoardDTOS, pageable, true);
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
    public ReviewBoardDTO findById_QueryDSL(Long id) {
        ReviewBoardDTO reviewBoardDTO = query.select(new QReviewBoardDTO(
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
                .where(reviewBoard.id.eq(id))
                .fetchOne();

        return reviewBoardDTO;
    }

    @Override
    public List<ReviewBoard> findAllByMemberIdDescWithPaging_QueryDSL(Member member) {
        List<ReviewBoard> reviewBoardList = query.select(reviewBoard)
                .from(reviewBoard)
                .where(reviewBoard.member.eq(member))
                .orderBy(reviewBoard.id.desc())
                .fetch();

        return reviewBoardList;
    }
}
