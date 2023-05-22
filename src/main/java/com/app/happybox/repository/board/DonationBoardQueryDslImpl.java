package com.app.happybox.repository.board;

import com.app.happybox.entity.board.DonationBoard;
import com.app.happybox.entity.board.ReviewBoard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static com.app.happybox.entity.board.QDonationBoard.donationBoard;
import static com.app.happybox.entity.board.QReviewBoard.reviewBoard;


@RequiredArgsConstructor
@Slf4j
public class DonationBoardQueryDslImpl implements DonationBoardQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public DonationBoard getCurrentSequence_QueryDsl() {
        return query.select(donationBoard)
                .from(donationBoard)
                .orderBy(donationBoard.id.desc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public Slice<DonationBoard> findAllByIdDescWithPaging_QueryDSL(Pageable pageable) {
        List<DonationBoard> donationBoards = query.select(donationBoard)
                .from(donationBoard)
                .join(donationBoard.welfare).fetchJoin()
                .join(donationBoard.donationBoardFiles).fetchJoin()
                .orderBy(donationBoard.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return checkLastPage(pageable, donationBoards);
    }

    @Override
    public Slice<DonationBoard> findAllByPointDescWithPaging_QueryDSL(Pageable pageable) {
        List<DonationBoard> donationBoards = query.select(donationBoard)
                .from(donationBoard)
                .join(donationBoard.welfare).fetchJoin()
                .join(donationBoard.donationBoardFiles).fetchJoin()
                .orderBy(donationBoard.welfare.welfarePointTotal.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return checkLastPage(pageable, donationBoards);
    }

    @Override
    public List<DonationBoard> findTop3OrderByDate_QueryDSL() {
        List<DonationBoard> fetch = query.select(donationBoard)
                .from(donationBoard)
                .join(donationBoard.welfare)
                .leftJoin(donationBoard.donationBoardFiles).fetchJoin()
                .orderBy(donationBoard.createdDate.desc())
                .limit(3L)
                .fetch();
        return fetch;
    }

    @Override
    public Page<DonationBoard> findAllWithPaging_QueryDSL(Pageable pageable) {
        List<DonationBoard> donationBoardList = query.select(donationBoard)
                .from(donationBoard)
                .leftJoin(donationBoard.donationBoardFiles).fetchJoin()
                .join(donationBoard.welfare).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(donationBoard.id.count()).from(donationBoard).fetchOne();

        return new PageImpl<>(donationBoardList, pageable, count);
    }

    //    hasNext true인지 false인지 체크하는 메소드(마지막 페이지 체크)
    private Slice<DonationBoard> checkLastPage(Pageable pageable, List<DonationBoard> donationBoards) {

        boolean hasNext = false;

        // 조회한 결과 개수가 요청한 페이지 사이즈보다 크면 뒤에 더 있음, next = true
        if (donationBoards.size() > pageable.getPageSize()) {
            hasNext = true;
            donationBoards.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(donationBoards, pageable, hasNext);
    }
}
