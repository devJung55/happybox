package com.app.happybox.repository.reply;

import com.app.happybox.entity.reply.RecipeBoardReply;
import com.app.happybox.entity.reply.ReviewBoardReply;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RecipeBoardReplyRepository extends JpaRepository<RecipeBoardReply, Long>, RecipeBoardReplyQueryDsl {
    //    최신순
    @Query("select r from RecipeBoardReply r join r.user where r.id = :id")
    public Slice<RecipeBoardReply> findAllByRecipeBoardId(Pageable pageable, Long id);

    //    인기순
    @Query("select r from RecipeBoardReply r join r.user where r.id = :id")
    public Slice<RecipeBoardReply> findAllByLikeDescByBoardId(Pageable pageable, Long id);
}
