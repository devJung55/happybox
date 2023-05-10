package com.app.happybox.entity.reply;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReplyService {

    public List<ReplyDTO> findAllByRefId(Pageable pageable, Long id);

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
}
