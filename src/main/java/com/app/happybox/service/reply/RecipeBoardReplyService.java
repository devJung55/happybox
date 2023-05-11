package com.app.happybox.service.reply;

import com.app.happybox.entity.reply.RecipeBoardReply;
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
    public Slice<ReplyDTO> findAllByRefId(Pageable pageable, Long id) {
        Slice<RecipeBoardReply> recipeBoardReplySlice = recipeBoardReplyRepository.findAllByRecipeBoardId(pageable, id);
        List<ReplyDTO> replyDTOList = recipeBoardReplySlice
                .get()
                .map(this::reviewBoardReplyToDTO)
                .collect(Collectors.toList());
        return new SliceImpl<>(replyDTOList, pageable, recipeBoardReplySlice.hasNext());
    }
}
