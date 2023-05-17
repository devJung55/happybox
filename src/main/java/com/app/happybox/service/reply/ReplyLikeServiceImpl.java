package com.app.happybox.service.reply;

import com.app.happybox.entity.reply.Reply;
import com.app.happybox.entity.reply.ReplyLike;
import com.app.happybox.entity.user.User;
import com.app.happybox.exception.ReplyNotFoundException;
import com.app.happybox.exception.UserNotFoundException;
import com.app.happybox.repository.reply.ReplyRepository;
import com.app.happybox.repository.reply.ReplyLikeRepository;
import com.app.happybox.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Qualifier("like")
public class ReplyLikeServiceImpl implements ReplyLikeService {
    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;
    private final ReplyLikeRepository replyLikeRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean checkOutLike(Long id, Long userId) {
        boolean check = replyLikeRepository.checkUserLikesReply_QueryDSL(id, userId);
        Reply reply = replyRepository.findById(id).orElseThrow(ReplyNotFoundException::new);

        if(check) {
            // 삭제
            replyLikeRepository.deleteUserLikeByUserAndReply(id, userId);

            // 댓글 좋아요 수 감소
            Integer replyLikeCount = reply.getReplyLikeCount();
            reply.setReplyLikeCount(--replyLikeCount);

        } else {
            User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

            // 저장
            replyLikeRepository.save(new ReplyLike(reply, user));

            // 댓글 좋아요 수 증가
            Integer replyLikeCount = reply.getReplyLikeCount();
            reply.setReplyLikeCount(++replyLikeCount);
        }

        return check;
    }
}
