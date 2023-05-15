package com.app.happybox.repository.reply;

import com.app.happybox.entity.reply.ProductReply;
import com.app.happybox.entity.reply.ReplyLike;
import com.app.happybox.entity.user.Member;
import com.app.happybox.repository.user.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional @Rollback(false)
@Slf4j
class ReplyLikeRepositoryTests {
    @Autowired
    private ReplyLikeRepository replyLikeRepository;

    @Autowired
    private ProductReplyRepository productReplyRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void saveTest() {
        // given
        Optional<Member> member = memberRepository.findById(1L);
        Optional<ProductReply> productReply = productReplyRepository.findById(10L);

        // when
        if (!member.isPresent() || !productReply.isPresent()) fail("member 혹은 productReply 없음.");

        if (replyLikeRepository.checkUserLikesReply_QueryDSL(member.get().getId(), productReply.get().getId())) {
            fail("이미 좋아요를 누른 댓글입니다.");
            return;
        }

        ReplyLike replyLike = new ReplyLike(productReply.get(), member.get());
        replyLikeRepository.save(replyLike);

        Integer replyLikeCount = productReply.get().getReplyLikeCount();
        productReply.get().setReplyLikeCount(++replyLikeCount);

        // then
        log.info(replyLikeRepository.toString());
    }

    @Test
    public void cancelLikeTest() {
        // given
        Optional<Member> member = memberRepository.findById(1L);
        Optional<ProductReply> productReply = productReplyRepository.findById(10L);

        // when
        if (!member.isPresent() || !productReply.isPresent()) fail("member 혹은 productReply 없음.");

        replyLikeRepository.deleteUserLikeByUserAndReply(member.get().getId(), productReply.get().getId());

        Integer replyLikeCount = productReply.get().getReplyLikeCount();
        productReply.get().setReplyLikeCount(--replyLikeCount);
        
        // then
        log.info("좋아요 눌렀는지 여부 : " + replyLikeRepository.checkUserLikesReply_QueryDSL(member.get().getId(), productReply.get().getId()));
    }
}