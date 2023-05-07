package com.app.happybox.repository.reply;

import com.app.happybox.entity.reply.ReviewBoardReply;
import com.app.happybox.entity.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewBoardReplyRepository extends JpaRepository<ReviewBoardReply, Long>, ReviewBoardReplyQueryDsl {

}