package com.app.happybox.entity.reply;

import com.app.happybox.entity.user.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity @Table(name = "TBL_REPLY_LIKE")
@DynamicInsert
@Getter @ToString @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyLike {
    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Reply reply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    public ReplyLike(Reply reply, User user) {
        this.reply = reply;
        this.user = user;
    }
}
