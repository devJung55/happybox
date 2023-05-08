package com.app.happybox.repository.board;

import com.app.happybox.entity.board.Board;
import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.entity.board.ReviewBoardDTO;
import com.app.happybox.entity.file.BoardFile;
import com.app.happybox.entity.reply.ReviewBoardReply;
import com.app.happybox.entity.type.FileRepresent;
import com.app.happybox.entity.user.User;
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

import java.util.*;

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
//        BoardFile boardFile = new BoardFile("2023/04/01", UUID.randomUUID().toString(), "후기7.png", FileRepresent.REPRESENT);
//        BoardFile boardFile2 = new BoardFile("2023/04/01", UUID.randomUUID().toString(), "후기8.png", FileRepresent.ORDINARY);
//        BoardFile boardFile3 = new BoardFile("2023/04/01", UUID.randomUUID().toString(), "후기9.png", FileRepresent.ORDINARY);
//        List<BoardFile> boardFiles = new ArrayList<>(Arrays.asList(boardFile, boardFile2, boardFile3));
//        boardFile.setBoard(reviewBoardRepository.findById(67L).get());
//        boardFile2.setBoard(reviewBoardRepository.findById(67L).get());
//        boardFile3.setBoard(reviewBoardRepository.findById(67L).get());
//
//        boardFileRepository.save(boardFile);
//        boardFileRepository.save(boardFile2);
//        boardFileRepository.save(boardFile3);


//        for(int i=0; i<3; i++){
//            ReviewBoard reviewBoard = new ReviewBoard("테스트 제목" + (i+1), "테스트 내용" + (i+1), i%5+1);
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
                PageRequest.of(0, 3)
        ).stream().map(ReviewBoard::toString).forEach(log::info);
    }

//    리뷰게시판 목록 인기순 조회
    @Test
    public void findAllByLikeCountDescWithPagingTest(){
        reviewBoardRepository.findAllByLikeCountDescWithPaging_QueryDSL(
                PageRequest.of(0, 3)
        ).stream().map(ReviewBoard::toString).forEach(log::info);
    }

//    리뷰게시판 상세보기
    @Test
    public void findByIdTest(){
        log.info(reviewBoardRepository.findById_QueryDSL(31L).toString());
        log.info(reviewBoardRepository.findById(31L).get().getBoardFiles().toString());
    }


//    마이페이지 리뷰 목록
    @Test
    public void findAllByMemberIdDescWithPagingTest() {
        reviewBoardRepository.findAllByMemberIdDescWithPaging_QueryDSL(PageRequest.of(0, 5), 1L)
                .stream().map(Board::getBoardFiles).forEach(v -> log.info(v.toString()));

    }
}
