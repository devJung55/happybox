package com.app.happybox.service.reply;

import com.app.happybox.entity.reply.Reply;
import com.app.happybox.entity.reply.ReplyDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ReplyService {

    public Slice<ReplyDTO> findAllByRefId(Pageable pageable, Long id);

    default <T extends Reply> ReplyDTO replyToDTO(T reply) {
        return ReplyDTO.builder()
                .id(reply.getId())
                .replyContent(reply.getReplyContent())
                .replyLikeCount(reply.getReplyLikeCount())
                .userRole(reply.getUser().getUserRole())
                .createdDate(reply.getCreatedDate())
                .updatedDate(reply.getUpdatedDate())
                .build();
    }
}
