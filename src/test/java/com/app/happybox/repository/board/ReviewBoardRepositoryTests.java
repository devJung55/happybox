package com.app.happybox.repository.board;

import com.app.happybox.entity.board.Board;
import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.user.Member;
import com.app.happybox.repository.subscript.SubscriptionRepository;
import com.app.happybox.repository.user.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional @Rollback(false)
@Slf4j
public class ReviewBoardRepositoryTests {
    @Autowired private ReviewBoardRepository reviewBoardRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private SubscriptionRepository subscriptionRepository;

    @Test
    public void saveTest() {
        Member member = memberRepository.findById(1L).get();
        Subscription subscription = subscriptionRepository.findById(20L).get();

        for (int i = 0; i < 5; i++) {
            ReviewBoard reviewBoard = new ReviewBoard("후기 내용_" + (i + 1), i + 1, i, member, subscription);
            reviewBoardRepository.save(reviewBoard);
        }
    }

    @Test
    public void findReviewBoardListByMemberIdWithPagingTest() {
        reviewBoardRepository.findReviewBoardListByMemberIdWithPaging_QueryDSL(memberRepository.findById(1L).get())
                .stream().map(Board::getBoardContent).forEach(log::info);
    }
}
