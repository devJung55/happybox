package com.app.happybox.entity.reply;

import com.sun.istack.NotNull;
import lombok.Builder;
import org.hibernate.annotations.ColumnDefault;

public class ReplyDTO {
    private Long id;
    private String replyContent;
    private Integer replyLikeCount;

    @Builder
    public ReplyDTO(Long id, String replyContent, Integer replyLikeCount) {
        this.id = id;
        this.replyContent = replyContent;
        this.replyLikeCount = replyLikeCount;
    }
}
