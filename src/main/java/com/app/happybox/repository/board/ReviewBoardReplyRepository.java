package com.app.happybox.repository.board;

import com.app.happybox.entity.reply.ReviewBoardReply;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewBoardReplyRepository extends JpaRepository<ReviewBoardReply, Long>, ReviewBoardReplyQueryDsl {
}
