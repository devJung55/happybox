package com.app.happybox.service.reply;

import com.app.happybox.entity.reply.RecipeBoardReply;
import com.app.happybox.entity.reply.Reply;
import com.app.happybox.entity.reply.ReplyDTO;
import com.app.happybox.entity.reply.ReviewBoardReply;
import com.app.happybox.repository.board.RecipeBoardRepository;
import com.app.happybox.repository.reply.RecipeBoardReplyRepository;
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
@Qualifier("RecipeBoard")
public class RecipeBoardReplyService implements ReplyService {
    private final RecipeBoardReplyRepository recipeBoardReplyRepository;

    @Override
    public Slice<ReplyDTO> findAllByRefId(Pageable pageable, Long id, Boolean isOrderByDate) {
        // 최신순 인기순 정렬 메소드 따로 만들기 (Query이용)
        Slice<RecipeBoardReply> recipeBoardReplySlice = null;
        isOrderByDate = isOrderByDate == null ? true : isOrderByDate;

        if(isOrderByDate) {
            recipeBoardReplySlice = recipeBoardReplyRepository.findAllByRecipeBoardId(pageable, id);
        } else recipeBoardReplySlice = recipeBoardReplyRepository.findAllByReviewBoardIdOrderByLikeCount(pageable, id);

        List<ReplyDTO> replyDTOList = recipeBoardReplySlice
                .get()
                .map(this::replyToDTO)
                .collect(Collectors.toList());
        return new SliceImpl<>(replyDTOList, pageable, recipeBoardReplySlice.hasNext());
    }

    @Override
    public ReplyDTO saveReply(ReplyDTO replyDTO, Long refId, Long userId) {
        return null;
    }

    @Override
    public void deleteReply(ReplyDTO replyDTO, Long refId, Long userId) {

    }

    @Override
    public RecipeBoardReply replyToEntity(ReplyDTO replyDTO, Long refId, Long userId) {
        return null;
    }
}
