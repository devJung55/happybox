package com.app.happybox.repository.board;

import com.app.happybox.entity.board.Board;
import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.entity.board.ReviewBoardDTO;
import com.app.happybox.repository.subscript.SubscriptionRepository;
import com.app.happybox.repository.user.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(false)
@Slf4j
public class ReviewBoardRepositoryTests {
    @Autowired
    private ReviewBoardRepository reviewBoardRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;

//    리뷰게시판 세이브 테스트
    @Test
    public void saveTest(){
        for(int i=1; i<10; i++){
            ReviewBoard reviewBoard = new ReviewBoard("테스트 제목" + (i+1), "테스트 내용" + (i+1));
            memberRepository.findById(1L).ifPresent(member -> reviewBoard.setMember(member));
            subscriptionRepository.findById(27L).ifPresent(subscription -> reviewBoard.setSubscription(subscription));
            reviewBoardRepository.save(reviewBoard);
        }
    }

//    리뷰게시판 목록 최신순 조회
    @Test
    public void findAllByDateDescWithPagingTest(){
        reviewBoardRepository.findAllByDateDescWithPaging_QueryDSL(
                PageRequest.of(0, 5)
        ).stream().map(ReviewBoardDTO::toString).forEach(log::info);
    }

//    리뷰게시판 목록 인기순 조회
    @Test
    public void findAllByLikeCountDescWithPagingTest(){
        reviewBoardRepository.findAllByLikeCountDescWithPaging_QueryDSL(
                PageRequest.of(0, 5)
        ).stream().map(ReviewBoardDTO::toString).forEach(log::info);
    }

//    마이페이지 나의리뷰 조회
    @Test
    public void findAllByMemberIdDescWithPagingTest() {
        reviewBoardRepository.findAllByMemberIdDescWithPaging_QueryDSL(memberRepository.findById(1L).get())
                .stream().map(Board::getBoardContent).forEach(log::info);
    }
}
