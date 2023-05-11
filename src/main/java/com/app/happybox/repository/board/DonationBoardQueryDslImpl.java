package com.app.happybox.repository.board;

import com.app.happybox.entity.board.DonationBoard;
import com.app.happybox.entity.board.QDonationBoard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.app.happybox.entity.board.QDonationBoard.donationBoard;


@RequiredArgsConstructor
public class DonationBoardQueryDslImpl implements DonationBoardQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public Page<DonationBoard> findAllByIdDescWithPaging_QueryDSL(Pageable pageable) {
        List<DonationBoard> donationBoards = query.select(donationBoard)
                .from(donationBoard)
                .join(donationBoard.welfare).fetchJoin()
                .join(donationBoard.boardFiles).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return new PageImpl<>(donationBoards);
    }

    @Override
    public List<DonationBoard> findTop3OrderByDate_QueryDSL() {
        return query.select(donationBoard)
                .from(donationBoard)
                .join(donationBoard.welfare)
                .join(donationBoard.boardFiles).fetchJoin()
                .orderBy(donationBoard.createdDate.desc())
                .limit(3L)
                .fetch();
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
