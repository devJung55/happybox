package com.app.happybox.repository.reply;

import com.app.happybox.entity.reply.ProductReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductReplyRepository extends JpaRepository<ProductReply, Long>, ProductReplyQueryDsl {
}
