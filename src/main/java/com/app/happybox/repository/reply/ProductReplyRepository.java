package com.app.happybox.repository.reply;

import com.app.happybox.entity.reply.ProductReply;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductReplyRepository extends JpaRepository<ProductReply, Long>, ProductReplyQueryDsl {
//    상품 댓글 조회 (좋아요 순 정렬)
    @Query("select r from ProductReply r join r.user where r.product.id = :productId order by r.replyLikeCount desc")
    public Slice<ProductReply> findAllByProductIdOrderByLikeCount(Pageable pageable, Long productId);

//    상품 댓글 조회 (날짜순 정렬)
    @Query("select r from ProductReply r join r.user where r.product.id = :productId order by r.createdDate desc")
    public Slice<ProductReply> findAllByProductIdOrderByCreatedDate(Pageable pageable, Long productId);
}
