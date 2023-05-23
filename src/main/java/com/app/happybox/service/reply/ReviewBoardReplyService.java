package com.app.happybox.service.reply;

import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.entity.product.Product;
import com.app.happybox.entity.reply.*;
import com.app.happybox.entity.user.User;
import com.app.happybox.exception.ProductNotFoundException;
import com.app.happybox.exception.ReplyNotFoundException;
import com.app.happybox.exception.UserNotFoundException;
import com.app.happybox.repository.board.ReviewBoardRepository;
import com.app.happybox.repository.reply.ReviewBoardReplyRepository;
import com.app.happybox.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Qualifier("ReviewBoard")
public class ReviewBoardReplyService implements ReplyService {
    private final ReviewBoardReplyRepository reviewBoardReplyRepository;
    private final ReviewBoardRepository reviewBoardRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Slice<ReplyDTO> findAllByRefId(Pageable pageable, Long id, Boolean isReviewByDate) {
        // 최신순 인기순 정렬 메소드 따로 만들기 (Query이용)
        Slice<ReviewBoardReply> reviewBoardReplySlice = null;
        isReviewByDate = isReviewByDate == null ? true : isReviewByDate;

        if(isReviewByDate) {
            reviewBoardReplySlice = reviewBoardReplyRepository.findAllByReviewBoardId(pageable, id);
        } else reviewBoardReplySlice = reviewBoardReplyRepository.findAllByReviewBoardIdOrderByLikeCount(pageable, id);

        List<ReplyDTO> replyDTOList = reviewBoardReplySlice
                .get()
                .map(this::replyToDTO)
                .collect(Collectors.toList());
        return new SliceImpl<>(replyDTOList, pageable, reviewBoardReplySlice.hasNext());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReplyDTO saveReply(ReplyDTO replyDTO, Long refId, Long userId) {
        ReviewBoardReply reply = reviewBoardReplyRepository.save(replyToEntity(replyDTO, refId, userId));
        ReviewBoard reviewBoard = reviewBoardRepository.findById(refId).orElseThrow(ProductNotFoundException::new);

        // 상품 댓글 수 컬럼의 값을 증가시켜 준다
        int replyCount = reviewBoard.getReviewBoardReplyCount() + 1;
        reviewBoard.setReviewBoardReplyCount(replyCount);

        return replyToDTO(reply);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteReply(Long replyId, Long refId, Long userId) {
        reviewBoardReplyRepository.deleteById(replyId);
        ReviewBoard reviewBoard = reviewBoardRepository.findById(refId).orElseThrow(ProductNotFoundException::new);

        int replyCount = reviewBoard.getReviewBoardReplyCount() - 1;
        reviewBoard.setReviewBoardReplyCount(replyCount);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReplyDTO updateReply(Long replyId, ReplyDTO replyDTO) {
        ReviewBoardReply reviewBoardReply = reviewBoardReplyRepository.findById(replyId).orElseThrow(ReplyNotFoundException::new);
        reviewBoardReply.setReplyContent(replyDTO.getReplyContent());

        return replyToDTO(reviewBoardReply);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReviewBoardReply replyToEntity(ReplyDTO replyDTO, Long refId, Long userId) {
        ReviewBoard reviewBoard = reviewBoardRepository.findById(refId).orElseThrow(ProductNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        return ReviewBoardReply.builder()
                .reviewBoard(reviewBoard)
                .replyContent(replyDTO.getReplyContent())
                .user(user)
                .build();
    }
}
