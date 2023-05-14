package com.app.happybox.repository.reply;

import com.app.happybox.entity.reply.ProductReply;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductReplyRepository extends JpaRepository<ProductReply, Long>, ProductReplyQueryDsl {
//    상품 댓글 조회
    @Query("select r from ProductReply r join r.user where r.product.id = :productId order by r.id desc")
    public Slice<ProductReply> findAllByProductId(Pageable pageable, Long productId);
}
