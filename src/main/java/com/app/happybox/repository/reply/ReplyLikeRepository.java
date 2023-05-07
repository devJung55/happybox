package com.app.happybox.repository.reply;

import com.app.happybox.entity.reply.ReplyLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyLikeRepository extends JpaRepository<ReplyLike, Long>, ReplyLikeQueryDsl {
}
