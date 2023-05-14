package com.app.happybox.service.board;

import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.entity.board.ReviewBoardLike;
import com.app.happybox.entity.user.Member;
import com.app.happybox.repository.board.ReviewBoardLikeRepository;
import com.app.happybox.repository.board.ReviewBoardRepository;
import com.app.happybox.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.aspectj.bridge.MessageUtil.fail;

@Service
@RequiredArgsConstructor
public class ReviewBoardLikeServiceImpl implements ReviewBoardLikeService {
    private final ReviewBoardLikeRepository reviewBoardLikeRepository;
    private final ReviewBoardRepository reviewBoardRepository;
    private final MemberRepository memberRepository;

    @Override
    public void insertHeart(Long userId, Long reviewBoardId) {
        Optional<Member> member = memberRepository.findById(userId);
        Optional<ReviewBoard> reviewBoard = reviewBoardRepository.findById(reviewBoardId);

        if(!member.isPresent() || !reviewBoard.isPresent()) fail("멤버 혹은 리뷰 보드가 존재하지 않음");

        if(!reviewBoardLikeRepository.checkMemberLikesReviewBoard_QueryDSL(userId, reviewBoardId)){
            Integer likeCount = reviewBoard.get().getReviewLikeCount();

            ReviewBoardLike reviewBoardLike = new ReviewBoardLike(member.get(), reviewBoard.get());
            reviewBoardLikeRepository.save(reviewBoardLike);

            reviewBoard.get().setReviewLikeCount(++likeCount);
        }
    }
}
