package com.app.happybox.service.reply;

import com.app.happybox.entity.reply.Reply;
import com.app.happybox.entity.reply.ReplyDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ReplyService {

    public Slice<ReplyDTO> findAllByRefId(Pageable pageable, Long id, Boolean isOrderByDate);

    public ReplyDTO saveReply(ReplyDTO replyDTO, Long refId, Long userId);

    public void deleteReply(Long replyId);

    default <T extends Reply> ReplyDTO replyToDTO(T reply) {
        return ReplyDTO.builder()
                .id(reply.getId())
                .replyContent(reply.getReplyContent())
                .replyLikeCount(reply.getReplyLikeCount())
                .userRole(reply.getUser().getUserRole())
                .createdDate(reply.getCreatedDate())
                .updatedDate(reply.getUpdatedDate())
                .userId(reply.getUser().getUserId())
                .build();
    }

    // To Entity 메서드, @Overide 하여 재정의 할 것. RefId는 댓글이 달린 테이블
    public <T extends Reply> T replyToEntity(ReplyDTO replyDTO, Long refId, Long userId);
}
