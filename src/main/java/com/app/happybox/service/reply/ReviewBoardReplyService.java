package com.app.happybox.service.reply;

import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.entity.reply.ProductReply;
import com.app.happybox.entity.reply.Reply;
import com.app.happybox.entity.reply.ReplyDTO;
import com.app.happybox.entity.reply.ReviewBoardReply;
import com.app.happybox.repository.reply.ReviewBoardReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Qualifier("ReviewBoard")
public class ReviewBoardReplyService implements ReplyService {
    private final ReviewBoardReplyRepository reviewBoardReplyRepository;

    @Override
    public Slice<ReplyDTO> findAllByRefId(Pageable pageable, Long id, Boolean isOrderByDate) {
        // 최신순 인기순 정렬 메소드 따로 만들기 (Query이용)
        Slice<ReviewBoardReply> reviewBoardReplySlice = null;
        isOrderByDate = isOrderByDate == null ? true : isOrderByDate;

        if(isOrderByDate) {
            reviewBoardReplySlice = reviewBoardReplyRepository.findAllByReviewBoardId(pageable, id);
        } else reviewBoardReplySlice = reviewBoardReplyRepository.findAllByReviewBoardIdOrderByLikeCount(pageable, id);

        List<ReplyDTO> replyDTOList = reviewBoardReplySlice
                .get()
                .map(this::replyToDTO)
                .collect(Collectors.toList());
        return new SliceImpl<>(replyDTOList, pageable, reviewBoardReplySlice.hasNext());
    }

    @Override
    public ReplyDTO saveReply(ReplyDTO replyDTO, Long refId, Long userId) {
        return null;
    }

    @Override
    public ReviewBoardReply replyToEntity(ReplyDTO replyDTO, Long refId, Long userId) {
        return null;
    }
}
