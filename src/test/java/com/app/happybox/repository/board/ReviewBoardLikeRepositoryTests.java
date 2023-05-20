//package com.app.happybox.repository.board;
//
//import com.app.happybox.entity.board.ReviewBoard;
//import com.app.happybox.entity.board.ReviewBoardLike;
//import com.app.happybox.entity.user.Member;
//import com.app.happybox.repository.subscript.SubscriptizonRepository;
//import com.app.happybox.repository.user.MemberRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.fail;
//
//@SpringBootTest
//@Transactional
//@Rollback(false)
//@Slf4j
//public class ReviewBoardLikeRepositoryTests {
//    @Autowired
//    private ReviewBoardLikeRepository reviewBoardLikeRepository;
//    @Autowired
//    private ReviewBoardRepository reviewBoardRepository;
//    @Autowired
//    private MemberRepository memberRepository;
//    @Autowired
//    private SubscriptionRepository subscriptionRepository;
//
//    @Test
//    public void checkMemberLikesReviewBoardTest(){
//        Optional<Member> member = memberRepository.findById(1L);
//        Optional<ReviewBoard> reviewBoard = reviewBoardRepository.findById(39L);
//
//        if(!member.isPresent() || !reviewBoard.isPresent()) fail("멤버 혹은 리뷰 보드가 존재하지 않음");
//
//        checkMemberAlreadyLikes(member.get(), reviewBoard.get());
//    }
//
//    @Test
//    public void saveTest(){
//        Optional<Member> member = memberRepository.findById(1L);
//        Optional<ReviewBoard> reviewBoard = reviewBoardRepository.findById(67L);
//
//        if(!member.isPresent() || !reviewBoard.isPresent()) fail("멤버 혹은 리뷰 보드가 존재하지 않음");
//
//        if(!checkMemberAlreadyLikes(member.get(), reviewBoard.get())){
//            Integer likeCount = reviewBoard.get().getReviewLikeCount();
//
//            ReviewBoardLike reviewBoardLike = new ReviewBoardLike(member.get(), reviewBoard.get());
//            reviewBoardLikeRepository.save(reviewBoardLike);
//
//            reviewBoard.get().setReviewLikeCount(++likeCount);
//        }
//    }
//
//    private boolean checkMemberAlreadyLikes(Member member, ReviewBoard reviewBoard){
//        return reviewBoardLikeRepository.checkMemberLikesReviewBoard_QueryDSL(member, reviewBoard);
//    }
//}
