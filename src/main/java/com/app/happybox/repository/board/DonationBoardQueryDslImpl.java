package com.app.happybox.repository.board;

import com.app.happybox.entity.board.DonationBoard;
import com.app.happybox.entity.board.QDonationBoard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.app.happybox.entity.board.QDonationBoard.donationBoard;

@RequiredArgsConstructor
public class DonationBoardQueryDslImpl implements DonationBoardQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public Optional<DonationBoard> findById_QueryDSL(Long id) {
        return Optional.of(query.select(donationBoard)
                .from(donationBoard)
                .join(donationBoard.welfare)
                .join(donationBoard.boardFiles)
                .fetchJoin()
                .where(donationBoard.id.eq(id))
                .fetchOne());
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


}
