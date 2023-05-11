package com.app.happybox.service.reply;

import com.app.happybox.entity.reply.Reply;
import com.app.happybox.entity.reply.ReplyDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ReplyService {

    public Slice<ReplyDTO> findAllByRefId(Pageable pageable, Long id);

    default <T extends Reply> ReplyDTO productReplyToDTO(T productReply) {
        return ReplyDTO.builder()
                .id(productReply.getId())
                .replyContent(productReply.getReplyContent())
                .replyLikeCount(productReply.getReplyLikeCount())
                .userRole(productReply.getUser().getUserRole())
                .createdDate(productReply.getCreatedDate())
                .updatedDate(productReply.getUpdatedDate())
                .build();
    }

    default <T extends Reply> ReplyDTO reviewBoardReplyToDTO(T reviewBoardReply) {
        return ReplyDTO.builder()
                .id(reviewBoardReply.getId())
                .replyContent(reviewBoardReply.getReplyContent())
                .replyLikeCount(reviewBoardReply.getReplyLikeCount())
                .userRole(reviewBoardReply.getUser().getUserRole())
                .createdDate(reviewBoardReply.getCreatedDate())
                .updatedDate(reviewBoardReply.getUpdatedDate())
                .build();
    }

    default <T extends Reply> ReplyDTO recipeBoardReplyToDTO(T recipeBoardReply) {
        return ReplyDTO.builder()
                .id(recipeBoardReply.getId())
                .replyContent(recipeBoardReply.getReplyContent())
                .replyLikeCount(recipeBoardReply.getReplyLikeCount())
                .userRole(recipeBoardReply.getUser().getUserRole())
                .createdDate(recipeBoardReply.getCreatedDate())
                .updatedDate(recipeBoardReply.getUpdatedDate())
                .build();
    }

}
