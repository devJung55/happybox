package com.app.happybox.repository.board;

import com.app.happybox.entity.board.DonationBoard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.app.happybox.entity.board.QDonationBoard.donationBoard;


@RequiredArgsConstructor
@Slf4j
public class DonationBoardQueryDslImpl implements DonationBoardQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public Page<DonationBoard> findAllByIdDescWithPaging_QueryDSL(Pageable pageable) {
        List<DonationBoard> donationBoards = query.select(donationBoard)
                .from(donationBoard)
                .join(donationBoard.welfare).fetchJoin()
                .join(donationBoard.donationBoardFiles).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return new PageImpl<>(donationBoards);
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
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(donationBoard.id.count()).from(donationBoard).fetchOne();

        return new PageImpl<>(donationBoardList, pageable, count);
    }
}
