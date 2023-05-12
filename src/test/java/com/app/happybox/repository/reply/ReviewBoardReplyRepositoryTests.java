package com.app.happybox.repository.reply;

import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.entity.reply.Reply;
import com.app.happybox.entity.reply.ReviewBoardReply;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.User;
import com.app.happybox.repository.board.ReviewBoardRepository;
import com.app.happybox.repository.user.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional @Rollback(false)
@Slf4j
public class ReviewBoardReplyRepositoryTests {
    @Autowired private ReviewBoardReplyRepository reviewBoardReplyRepository;
    @Autowired private ReviewBoardRepository reviewBoardRepository;
    @Autowired private MemberRepository memberRepository;

    @Test
    public void saveTest() {
//        for (int i = 0; i < 5; i++){
//            ReviewBoardReply reviewBoardReply = new ReviewBoardReply(
//                    "리뷰 댓글_" + (i + 1),
//                    memberRepository.findById(1L).get(),
//                    reviewBoardRepository.findById(65L).get()
//            );
//
//            reviewBoardReplyRepository.save(reviewBoardReply);
//
//        }
        for (int i=0; i<5; i++){
            Optional<Member> member = memberRepository.findById(1L);
            Optional<ReviewBoard> reviewBoard = reviewBoardRepository.findById(65L);
            Integer reviewBoardReplyCount = reviewBoard.get().getReviewBoardReplyCount();

            ReviewBoardReply reviewBoardReply = new ReviewBoardReply("댓글테스트" + (i+1), member.get(), reviewBoard.get());
            reviewBoardReplyRepository.save(reviewBoardReply);
            reviewBoard.get().setReviewBoardReplyCount(++reviewBoardReplyCount);
        }

    }

    @Test
    public void findAllWithScroll(){
//        reviewBoardReplyRepository.findAllWithScroll(PageRequest.of(0, 10))
//                .stream().map(Reply::getReplyContent).forEach(log::info);
    }

    @Test
    public void findAllByLikeDescWithScroll(){
//        reviewBoardReplyRepository.findAllWithScroll(PageRequest.of(0, 10))
//                .stream().map(Reply::getReplyContent).forEach(log::info);
    }

    @Test
    public void findAllByMemberIdDescWithPagingTest() {
        reviewBoardReplyRepository.findAllByMemberIdDescWithPaging_QueryDSL(PageRequest.of(0, 3), 1L)
                .stream().map(Reply::getReplyContent).forEach(log::info);
    }
}