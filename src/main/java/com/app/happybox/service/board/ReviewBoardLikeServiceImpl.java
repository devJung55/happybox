package com.app.happybox.service.board;

import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.entity.board.ReviewBoardLike;
import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.subscript.SubscriptionLike;
import com.app.happybox.entity.user.Member;
import com.app.happybox.exception.BoardNotFoundException;
import com.app.happybox.exception.SubscriptionNotFoundException;
import com.app.happybox.exception.UserNotFoundException;
import com.app.happybox.repository.board.ReviewBoardLikeRepository;
import com.app.happybox.repository.board.ReviewBoardRepository;
import com.app.happybox.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.aspectj.bridge.MessageUtil.fail;

@Service
@RequiredArgsConstructor
public class ReviewBoardLikeServiceImpl implements ReviewBoardLikeService {
    private final ReviewBoardLikeRepository reviewBoardLikeRepository;
    private final ReviewBoardRepository reviewBoardRepository;
    private final MemberRepository memberRepository;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean checkOutLike(Long reviewBoardId, Long memberId) {
        boolean check = reviewBoardLikeRepository.checkMemberLikesReviewBoard_QueryDSL(reviewBoardId, memberId);
        ReviewBoard reviewBoard = reviewBoardRepository.findById(reviewBoardId).orElseThrow(BoardNotFoundException::new);

        if(check) {
            // 삭제
            reviewBoardLikeRepository.deleteUserLikeByUserAndReviewBoard(reviewBoardId, memberId);

            // 좋아요 수 감소
            Integer reviewLikeCount = reviewBoard.getReviewLikeCount();
            reviewBoard.setReviewLikeCount(--reviewLikeCount);

        } else {
            Member member = memberRepository.findById(memberId).orElseThrow(UserNotFoundException::new);

            // 저장
            reviewBoardLikeRepository.save(new ReviewBoardLike(reviewBoard, member));

            // 구독 좋아요 수 증가
            Integer reviewLikeCount = reviewBoard.getReviewLikeCount();
            reviewBoard.setReviewLikeCount(++reviewLikeCount);
        }

        return check;
    }

    @Override
    public boolean checkLike(Long reviewBoardId, Long memberId) {
        return reviewBoardLikeRepository.checkMemberLikesReviewBoard_QueryDSL(reviewBoardId, memberId);
    }
}
