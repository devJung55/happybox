package com.app.happybox.repository.board;

import com.app.happybox.entity.board.Board;
import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.entity.file.BoardFile;
import com.app.happybox.type.FileRepresent;
import com.app.happybox.repository.reply.ReviewBoardReplyRepository;
import com.app.happybox.repository.subscript.SubscriptionRepository;
import com.app.happybox.repository.user.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
    @Autowired
    private BoardFileRepository boardFileRepository;
    @Autowired
    private ReviewBoardReplyRepository reviewBoardReplyRepository;

//    리뷰게시판 세이브 테스트
    @Test
    public void saveTest(){
        for(Long i=170L; i<180L; i++){
            BoardFile boardFile = new BoardFile("2023/04/01", UUID.randomUUID().toString(), "대표.png", FileRepresent.REPRESENT);
            BoardFile boardFile2 = new BoardFile("2023/04/01", UUID.randomUUID().toString(), "일반.png", FileRepresent.ORDINARY);
            BoardFile boardFile3 = new BoardFile("2023/04/01", UUID.randomUUID().toString(), "일반.png", FileRepresent.ORDINARY);
            boardFile.setBoard(reviewBoardRepository.findById(i).get());
            boardFile2.setBoard(reviewBoardRepository.findById(i).get());
            boardFile3.setBoard(reviewBoardRepository.findById(i).get());

            boardFileRepository.save(boardFile);
            boardFileRepository.save(boardFile2);
            boardFileRepository.save(boardFile3);
        }


//        for(int i=0; i<10; i++){
//            ReviewBoard reviewBoard = new ReviewBoard("테스트 제목1" + (i+1), "테스트 내용1" + (i+1), i%5+1);
//            memberRepository.findById(1L).ifPresent(member -> reviewBoard.setMember(member));
//            subscriptionRepository.findById(3L).ifPresent(subscription -> reviewBoard.setSubscription(subscription));
//            subscriptionRepository.findById(27L).ifPresent(subscription -> reviewBoard.setSubscription(subscription));
//            reviewBoardRepository.save(reviewBoard);
//        }

    }

//    리뷰게시판 목록 최신순 조회
    @Test
    public void findAllByDateDescWithPagingTest(){
        reviewBoardRepository.findAllByIdDescWithPaging_QueryDSL(
                PageRequest.of(0, 5)
        ).stream().map(ReviewBoard::toString).forEach(log::info);
    }

//    리뷰게시판 목록 인기순 조회
    @Test
    public void findAllByLikeCountDescWithPagingTest(){
        reviewBoardRepository.findAllByLikeCountDescWithPaging_QueryDSL(
                PageRequest.of(0, 10)
        ).stream().map(ReviewBoard::toString).forEach(log::info);
    }

//    리뷰게시판 상세보기
    @Test
    public void findByIdTest(){
        log.info(reviewBoardRepository.findById(65L).toString());
        log.info(reviewBoardRepository.findById(65L).get().getSubscription().getWelfare().getWelfareName().toString());
    }

//    리뷰게시판 수정
    @Test
    public void updateTest(){
        reviewBoardRepository.findById(65L).ifPresent(reviewBoard -> {reviewBoard.setBoardTitle("수정제목1"); reviewBoard.setBoardContent("수정내용1");});

    }


//    마이페이지 리뷰 목록
    @Test
    public void findAllByMemberIdDescWithPagingTest() {
        reviewBoardRepository.findAllByMemberIdDescWithPaging_QueryDSL(PageRequest.of(0, 5), 1L)
                .stream().map(Board::getBoardFiles).forEach(v -> log.info(v.toString()));
    }

    @Test
    public void findReviewBoardListDescWithPaging_QueryDSL_Test() {
        reviewBoardRepository.findReviewBoardListDescWithPaging_QueryDSL(PageRequest.of(0, 5))
                .stream().map(ReviewBoard::toString).forEach(log::info);
    }
}
