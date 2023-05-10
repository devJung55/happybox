package com.app.happybox.entity.reply;

import com.app.happybox.type.Role;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Getter @ToString
public class ReplyDTO {
    private Long id;
    private String replyContent;
    private Integer replyLikeCount;
    private Role userRole;

    /* ================== */
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @Builder
    public ReplyDTO(Long id, String replyContent, Integer replyLikeCount, Role userRole, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.replyContent = replyContent;
        this.replyLikeCount = replyLikeCount;
        this.userRole = userRole;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
