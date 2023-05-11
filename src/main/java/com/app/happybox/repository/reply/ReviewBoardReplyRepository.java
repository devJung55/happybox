package com.app.happybox.repository.reply;

import com.app.happybox.entity.reply.ReviewBoardReply;
import com.app.happybox.entity.user.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewBoardReplyRepository extends JpaRepository<ReviewBoardReply, Long>, ReviewBoardReplyQueryDsl {
    //    최신순
    @Query("select r from ReviewBoardReply r join r.user where r.id = :id")
    public Slice<ReviewBoardReply> findAllByReviewBoardId(Pageable pageable, Long id);

    //    인기순
    @Query("select r from ReviewBoardReply r join r.user")
    public Slice<ReviewBoardReply> findAllByLikeDescWithScroll(Pageable pageable);
}